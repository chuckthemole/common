package com.rumpus.common.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.IUserDao;
import com.rumpus.common.Exception.User.UserAlreadyExistsException;
import com.rumpus.common.Exception.User.UserCreationException;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.IUserFactory;
import com.rumpus.common.User.Requests.CreateUserRequest;

abstract public class AbstractUserService<USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>>
        extends
            AbstractService<USER>
        implements
            IUserService<USER, USER_META> {

    protected IUserDao<USER, USER_META> userDao; // TODO: should this be private?

    private final IUserFactory<USER, USER_META> userFactory;

    private final PasswordEncoder passwordEncoder;

    public AbstractUserService(
            IUserDao<USER, USER_META> userDao,
            IUserFactory<USER, USER_META> userFactory,
            PasswordEncoder passwordEncoder) {
        super(userDao);
        this.userDao = userDao;
        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG_THIS("loadUserByUsername(username)");
        return this.userDao.loadUserByUsername(username);
    }

    @Override
    public USER getByUsername(String username) {
        LOG_THIS("getByUsername(username)");
        return this.userDao.getByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.getByUsername(username) != null;
    }

    @Override
    public USER createUser(CreateUserRequest request) {

        LOG_THIS("UserService::createUser()");

        if (request == null) {
            throw new UserCreationException("Request cannot be null");
        }

        final String username = request.getUsername();

        if (this.existsByUsername(username)) {
            LOG_THIS("User already exists: " + username);
            throw new UserAlreadyExistsException(username);
        }

        USER user = this.userFactory.createUser(request);
        String encodedPassword = this.passwordEncoder.encode(request.getPassword());
        user.setEncodedPassword(encodedPassword);

        USER savedUser = this.add(user);

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

        if (username == null || username.isBlank()) {
            throw new UserCreationException("Username cannot be null or empty");
        }

        if (this.existsByUsername(username)) {
            LOG_THIS("User already exists: " + username);
            throw new UserAlreadyExistsException(username);
        }

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

        USER savedUser = this.add(user);

        if (savedUser == null) {
            throw new UserCreationException("Failed to persist user: " + username);
        }

        return savedUser;
    }

    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getKey'");
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(AbstractUserService.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(AbstractUserService.class, level, args);
    }
}
