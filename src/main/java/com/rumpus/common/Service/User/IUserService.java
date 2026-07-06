package com.rumpus.common.Service.User;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.rumpus.common.Service.IService;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserCollection.Sort;
import com.rumpus.common.User.AbstractCommonUserCollection.SortDirection;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.Requests.CreateUserRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service("userDetailsService") // TODO: look at this annotation and see if we could put in IService?
                               // or remove it? - chuck
public interface IUserService<USER extends AbstractCommonUser<USER, META>, META extends AbstractCommonUserMetaData<META>>
        extends
        IService<USER>,
        UserDetailsService {

    /**
     * Get a user by their username.
     *
     * @param username
     *                 The username of the user to get.
     * @return The user with the given username. If no user is found, return null.
     *         If more than one user is found, return null. TODO: I don't like this.
     *         I think it should throw an exception if more than one user is found
     *         or maybe an Optional. - chuck
     */
    public USER getByUsername(String username);

    /**
     * Get the user details for a user with the given username.
     *
     * @param username
     *                 The username of the user to get the details for.
     * @return The {@link UserDetails} for the user with the given username.
     */
    public UserDetails loadUserByUsername(String username);

    /**
     * Check if a user exists with the given username.
     *
     * @param username
     *                 the username to check
     *
     * @return true if a user exists, false otherwise
     */
    boolean existsByUsername(String username);

    public List<USER> getAllUsers(Sort sort, SortDirection direction);

    /**
     * Create a new user from a {@link CreateUserRequest}.
     * <p>
     * This method is responsible for:
     * <ul>
     * <li>Validating uniqueness of username</li>
     * <li>Assigning a generated UUID</li>
     * <li>Setting default metadata</li>
     * <li>Assigning default roles/authorities</li>
     * </ul>
     *
     * @param request
     *                the user creation request containing required fields
     *
     * @return the persisted {@link USER} entity
     *
     * @throws IllegalArgumentException
     *                                  if username already exists or request is
     *                                  invalid
     */
    USER createUser(CreateUserRequest request);

    /**
     * Create a new user from an existing {@link AbstractCommonUser} instance.
     * <p>
     * This method is responsible for:
     * <ul>
     * <li>Validating uniqueness of username</li>
     * <li>Assigning a generated UUID (if not already set)</li>
     * <li>Ensuring required metadata is initialized</li>
     * <li>Assigning default roles/authorities (if missing)</li>
     * <li>Persisting the user entity</li>
     * </ul>
     *
     * <p>
     * This overload is intended for cases where a user object is already
     * constructed (e.g. from factories, mappers, or internal services).
     *
     * @param user
     *             the fully or partially constructed user entity
     *
     * @return the persisted {@link USER} entity
     *
     * @throws IllegalArgumentException
     *                                  if username already exists or user data is
     *                                  invalid
     */
    USER createUser(USER user);

    /**
     * Get all roles assigned to a user.
     *
     * @param userId the user id
     * @return list of role names assigned to the user
     */
    List<String> getUserRoles(UUID userId);

    /**
     * Add a role to a user.
     * <p>
     * This method should be idempotent:
     * adding an existing role should not create duplicates or error.
     *
     * @param userId the user id
     * @param role   the role to assign
     *
     * @throws IllegalArgumentException if user does not exist or role is invalid
     */
    void addUserRole(UUID userId, String role);

    /**
     * Remove a role from a user.
     * <p>
     * If the user does not have the role, the operation should be a no-op.
     *
     * @param userId the user id
     * @param role   the role to remove
     *
     * @throws IllegalArgumentException if user does not exist
     */
    void removeUserRole(UUID userId, String role);

    /**
     * Authenticate a user and establish an authenticated HTTP session.
     * <p>
     * This method is responsible for:
     * <ul>
     * <li>Authenticating the supplied username and password through the servlet
     * container</li>
     * <li>Creating or retrieving the current {@link HttpSession}</li>
     * <li>Marking the session as authenticated</li>
     * <li>Persisting the current Spring Security context in the session</li>
     * </ul>
     *
     * <p>
     * Implementations should throw an exception if authentication fails rather than
     * silently ignoring errors whenever possible.
     *
     * @param username
     *                 the username of the user to authenticate
     *
     * @param password
     *                 the user's plaintext password used for authentication
     *
     * @param request
     *                 the current {@link HttpServletRequest} used to establish the
     *                 authenticated session
     *
     * @throws jakarta.servlet.ServletException
     *                                          if the servlet container fails to
     *                                          authenticate the user
     */
    public void loginUser(
            String username,
            String password,
            HttpServletRequest request);

    /**
     * Get the key for this service. TODO: look into this more. Am I using this??
     *
     * @return
     */
    public String getKey();
}
