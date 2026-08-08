package com.rumpus.common.Service.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.User.IUserDao;
import com.rumpus.common.Exception.User.UserAlreadyExistsException;
import com.rumpus.common.Exception.User.UserCreationException;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Service.AbstractService;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserCollection;
import com.rumpus.common.User.AbstractCommonUserCollection.Sort;
import com.rumpus.common.User.AbstractCommonUserCollection.SortDirection;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.CommonUserDetails;
import com.rumpus.common.User.IUserFactory;
import com.rumpus.common.User.Requests.CreateUserRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

abstract public class AbstractUserService<USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>>
        extends
            AbstractService<USER>
        implements
            IUserService<USER, USER_META> {

    protected IUserDao<USER, USER_META> userDao; // TODO: should this be private?
    protected UserSecurityService userSecurityService;

    private final IUserFactory<USER, USER_META> userFactory;

    private final PasswordEncoder passwordEncoder;

    public AbstractUserService(
            IUserDao<USER, USER_META> userDao,
            UserSecurityService userSecurityService,
            IUserFactory<USER, USER_META> userFactory,
            PasswordEncoder passwordEncoder) {
        super(userDao);
        this.userDao = userDao;
        this.userSecurityService = userSecurityService;
        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG_THIS("loadUserByUsername(username)");
        return this.userSecurityService.loadUserByUsername(username);
    }

    @Override
    public USER getByUsername(String username) {
        LOG_THIS("getByUsername(username)");

        USER user = this.userDao.getByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found: " + username));

        CommonUserDetails userDetails = CommonUserDetails
                .createFromUserDetails(this.userSecurityService.loadUserByUsername(username));

        user.setUserDetails(userDetails);
        return user;
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.userDao.getByUsername(username).isPresent();
    }

    @Override
    public List<USER> getAllUsers(
            Sort sort,
            SortDirection direction) {

        LOG_THIS("AbstractUserService::getAllUsers(sort, direction)");
        LOG_THIS("Raw sort param: " + sort);
        LOG_THIS("Raw direction param: " + direction);

        List<USER> users = getAll();

        LOG_THIS("Total users fetched: " + users.size());

        if (users.isEmpty()) {
            LOG_THIS("[WARN] No users found in database/service");
            return users;
        }

        populateUserDetails(users);

        users = sortUsers(users, sort, direction);

        return users;
    }

    @Override
    public USER createUser(CreateUserRequest request) {

        LOG_THIS("UserService::createUser()");

        if (request == null) {
            throw new UserCreationException("Request cannot be null");
        }

        final String username = request.getUsername();
        validateUsername(username);

        USER user = this.userFactory.createUser(request);
        String encodedPassword = this.passwordEncoder.encode(request.getPassword());
        user.setEncodedPassword(encodedPassword);
        this.userSecurityService.createUser(user.getUserDetails());

        final USER savedUser = this.add(user);

        if (savedUser == null) {
            throw new UserCreationException("Failed to persist user: " + username);
        }

        return savedUser;
    }

    @Override
    public USER createUser(USER user) {

        LOG_THIS("UserService::createUser(USER)");

        if (user == null) {
            throw new UserCreationException("User cannot be null");
        }

        final String username = user.getUsername();
        validateUsername(username);

        /**
         * Ensure password is encoded.
         *
         * Assumption: - caller may provide raw OR already encoded password - we enforce
         * encoding here for safety
         */
        if (user.getEncodedPassword() == null || user.getEncodedPassword().isBlank()) {
            throw new UserCreationException("Password must be provided");
        }

        // Ensure metadata exists
        if (user.getMetaData() == null) {
            user.setMetaData(this.userFactory.createMetaData());
        }

        this.userSecurityService.createUser(user.getUserDetails());
        final USER savedUser = this.add(user);

        if (savedUser == null) {
            throw new UserCreationException("Failed to persist user: " + username);
        }

        return savedUser;
    }

    @Override
    public USER update(UUID id, USER updatedUser) {
        LOG("update()");
        final USER user = this.getById(id);
        final String username = user.getUsername();
        if (this.userSecurityService.userExists(username)
                && username.equals(updatedUser.getUsername())) {
            this.userSecurityService.updateUser(updatedUser.getUserDetails());
        } else {
            this.userSecurityService.deleteUser(username);
            this.userSecurityService.createUser(updatedUser.getUserDetails());
        }
        return this.dao.update(id, updatedUser);
    }

    @Override
    public boolean remove(UUID userId) {
        final String username = this.getById(userId).getUsername();
        this.userSecurityService.deleteUser(username);
        return this.remove(userId);
    }

    @Override
    public List<String> getUserRoles(UUID userId) {
        throw new UnsupportedOperationException("Unimplemented method 'getUserRoles'");
    }

    @Override
    public void addUserRole(UUID userId, String role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUserRole'");
    }

    @Override
    public void removeUserRole(UUID userId, String role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeUserRole'");
    }

    @Override
    public void loginUser(
            String username,
            final String password,
            HttpServletRequest request) {

        try {
            request.login(username, password);
        } catch (ServletException exception) {
            LOG_THIS("Unable to authenticate user: " + username);

            throw new AuthenticationServiceException(
                    "Unable to authenticate user: " + username,
                    exception);
        }

        HttpSession session = request.getSession();

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }

    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getKey'");
    }

    // Helpers

    /**
     * Populates the Spring Security details for each user.
     *
     * <p>
     * The persisted user model does not contain the runtime security information
     * required by Spring Security. This method loads the corresponding
     * {@link UserDetails} for each user and attaches it to the model.
     * </p>
     *
     * @param users
     *            users whose security details should be populated
     */
    protected void populateUserDetails(List<USER> users) {

        for (USER user : users) {

            UserDetails userDetails = userSecurityService.loadUserByUsername(user.getUsername());

            user.setUserDetails(
                    CommonUserDetails.createFromUserDetails(userDetails));
        }
    }

    /**
     * Sorts the supplied users according to the requested sort field and direction.
     *
     * <p>
     * Sorting is currently performed in memory. Future implementations should move
     * this responsibility into the persistence layer.
     * </p>
     *
     * @param users
     *            the users to sort
     * @param sort
     *            the sort field
     * @param direction
     *            the sort direction
     *
     * @return the sorted list
     */
    protected List<USER> sortUsers(
            List<USER> users,
            Sort sort,
            SortDirection direction) {

        List<USER> sortedUsers = sortInMemory(users, sort);

        if (direction == SortDirection.DESC) {
            Collections.reverse(sortedUsers);
        }

        return sortedUsers;
    }

    /**
     * Sorts users in ascending order according to the requested field.
     *
     * <p>
     * Descending order is handled separately by
     * {@link #sortUsers(List, Sort, SortDirection)}.
     * </p>
     *
     * @param users
     *            the users to sort
     * @param sort
     *            the sort field
     *
     * @return the sorted users
     */
    protected List<USER> sortInMemory(
            List<USER> users,
            Sort sort) {

        switch (sort) {

            case EMAIL :
                return AbstractCommonUserCollection
                        .getSortedByEmailListFromCollection(users);

            case ID :
                return AbstractCommonUserCollection
                        .getSortedByIdListFromCollection(users);

            case USERNAME :
            default :
                return AbstractCommonUserCollection
                        .getSortedByUsernameListFromCollection(users);
        }
    }

    // Helpers
    private void validateUsername(final String username) {
        if (username == null || username.isBlank()) {
            throw new UserCreationException("Username cannot be null or empty");
        }

        if (this.existsByUsername(username)) {
            LOG_THIS("User already exists: " + username);
            throw new UserAlreadyExistsException(username);
        }
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(AbstractUserService.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(AbstractUserService.class, level, args);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
