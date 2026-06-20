package com.rumpus.common.Controller.User;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.rumpus.common.Controller.ICommonController;
import com.rumpus.common.Service.User.IUserService;
import com.rumpus.common.Session.CommonSession;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserCollection;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.Requests.CreateUserRequest;
import com.rumpus.common.User.Requests.UpdateUserRoleRequest;
import com.rumpus.common.views.Template.IUserTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface ICommonUserController<
        /////////////////////////
        // Define generics here//
        /////////////////////////
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends IUserService<USER, USER_META>,
        USER_TEMPLATE extends IUserTemplate<USER, USER_META>>
        extends
            ICommonController {

    ///////////
    // Paths //
    ///////////
    public static final String PATH_USER = "/user";
    public static final String PATH_GET_USERS = "/users";
    public static final String PATH_GET_CURRENT_USER_NAME = "/username";
    public static final String PATH_GET_CURRENT_USER = "/current_user";
    public static final String PATH_GET_USERS_BY_SORT = "/users/{sort}";
    public static final String PATH_DELETE_USER = "/delete_user";
    public static final String PATH_UPDATE_USER = "/update_user";
    public static final String PATH_VALUE_GET_BY_USER_NAME = "/get_user_by_name/{username}";
    public static final String PATH_VALUE_GET_BY_USER_ID = "/user/{id}";
    public static final String PATH_VARIABLE_GET_BY_USER_NAME = "username";
    public static final String PATH_VARIABLE_GET_BY_USER_ID = "id";
    public static final String PATH_VARIABLE_SORT = "sort";

    /**
     * Retrieve all users sorted by the requested field and direction.
     * <p>
     * Supported sort fields:
     * <ul>
     * <li>{@link AbstractCommonUserCollection.Sort#USERNAME}</li>
     * <li>{@link AbstractCommonUserCollection.Sort#EMAIL}</li>
     * <li>{@link AbstractCommonUserCollection.Sort#ID}</li>
     * </ul>
     *
     * Supported sort directions:
     * <ul>
     * <li>{@link AbstractCommonUserCollection.SortDirection#ASC}</li>
     * <li>{@link AbstractCommonUserCollection.SortDirection#DESC}</li>
     * </ul>
     *
     * If no sort field is provided, results default to
     * {@link AbstractCommonUserCollection.Sort#USERNAME}.
     *
     * If no direction is provided, results default to
     * {@link AbstractCommonUserCollection.SortDirection#ASC}.
     *
     * @param sort
     *            the field used to sort users
     *
     * @param direction
     *            the direction to sort results
     *
     * @param session
     *            the current HTTP session
     *
     * @return a {@link ResponseEntity} containing the sorted list of users and the
     *         appropriate HTTP status
     */
    @GetMapping(value = ICommonUserController.PATH_GET_USERS)
    @Operation(summary = "Get all users", description = """
            Returns all users sorted by the requested field and direction.

            Supported sort fields:
            - USERNAME
            - EMAIL
            - ID

            Supported directions:
            - ASC
            - DESC
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid sort parameters")
    })
    public ResponseEntity<List<USER>> getAllUsers(
            @Parameter(description = "Field to sort by")
            @RequestParam(value = "sort", defaultValue = "username", required = false)
            AbstractCommonUserCollection.Sort sort,
            @Parameter(description = "Sort direction")
            @RequestParam(value = "direction", defaultValue = "ASC", required = false)
            AbstractCommonUserCollection.SortDirection direction,
            HttpSession session);

    /**
     * Create a new user account.
     * <p>
     * The request must contain all required information needed to create a user,
     * including any validation constraints defined on {@link CreateUserRequest}.
     * </p>
     *
     * If the request is valid and the user is created successfully, a new
     * authenticated session is established and returned.
     *
     * Possible outcomes:
     * <ul>
     * <li><b>201 Created</b> - User was created successfully.</li>
     * <li><b>400 Bad Request</b> - Request data failed validation.</li>
     * <li><b>409 Conflict</b> - A user with the supplied credentials already
     * exists.</li>
     * </ul>
     *
     * @param request
     *            the information required to create the user account
     *
     * @param servletRequest
     *            the current HTTP servlet request used to establish the
     *            authenticated session
     *
     * @return a {@link ResponseEntity} containing the created {@link CommonSession}
     *         and the appropriate HTTP status
     */
    @PostMapping(value = ICommonUserController.PATH_USER)
    @Operation(summary = "Create user", description = """
            Creates a new user account and returns the authenticated session.

            Possible outcomes:
            - 201 Created: User created successfully
            - 400 Bad Request: Invalid user data
            - 409 Conflict: User already exists
            """)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<CommonSession> userSubmit(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Information required to create a new user", required = true)
            @RequestBody
            CreateUserRequest request,
            HttpServletRequest servletRequest);

    @PutMapping("/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user role", description = "Updates the role assigned to a user. Admin only.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role updated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> updateUserRole(
            @PathVariable
            UUID userId,
            @RequestBody
            UpdateUserRoleRequest request);

    /**
     * Delete a user
     *
     * @param user
     *            the {@link USER} to delete
     * @param request
     *            the {@link HttpServletRequest} to use
     * @return the {@link CommonSession} as a {@link ResponseEntity}
     */
    @PostMapping(value = ICommonUserController.PATH_DELETE_USER)
    public ResponseEntity<CommonSession> deleteUser(@RequestBody
    String user,
            HttpServletRequest request);

    /**
     * Update a user
     *
     * @param user
     *            the {@link USER} to update
     * @param request
     *            the {@link HttpServletRequest} to use
     * @return the {@link CommonSession} as a {@link ResponseEntity}
     */
    @PostMapping(value = ICommonUserController.PATH_UPDATE_USER)
    public ResponseEntity<CommonSession> updateUser(@RequestBody
    USER user,
            HttpServletRequest request);

    /**
     * Get a user by username
     *
     * @TODO this should be secured so user info is not visible
     * @param username
     *            the username of the {@link USER} to get
     * @param request
     *            the {@link HttpServletRequest} to use
     * @return the {@link USER} as a {@link ResponseEntity}
     */
    @GetMapping(value = ICommonUserController.PATH_VALUE_GET_BY_USER_NAME)
    public ResponseEntity<USER> getUserByUsername(
            @PathVariable(ICommonUserController.PATH_VARIABLE_GET_BY_USER_NAME)
            String username,
            HttpServletRequest request);

    /**
     * Get a user by id
     *
     * @TODO this should be secured so user info is not visible
     * @param id
     *            the id of the {@link USER} to get
     * @param request
     *            the {@link HttpServletRequest} to use
     * @return the {@link USER} as a {@link ResponseEntity}
     */
    @GetMapping(value = ICommonUserController.PATH_VALUE_GET_BY_USER_ID)
    public ResponseEntity<USER> getUserById(
            @PathVariable(ICommonUserController.PATH_VARIABLE_GET_BY_USER_ID)
            String id,
            HttpServletRequest request);

    /**
     * Get the current user
     *
     * @param authentication
     *            the {@link Authentication} to use
     * @return the {@link USER} as a {@link ResponseEntity}
     */
    @GetMapping(value = ICommonUserController.PATH_GET_CURRENT_USER)
    public ResponseEntity<USER> getCurrentUser(Authentication authentication);

    /**
     * Get the current username
     *
     * @return the username as a {@link String}
     */
    @GetMapping(value = ICommonUserController.PATH_GET_CURRENT_USER_NAME)
    public ResponseEntity<String> currentUsername();

    // DEPRECATED
    /**
     * Get all users
     *
     * @deprecated getAllUsers is prefered.
     * @param sort
     *            the {@link AbstractCommonUserCollection.Sort} (as a String) to
     *            sort by
     * @param session
     *            the {@link HttpSession} to use
     * @return a list of all users as a {@link ResponseEntity}
     */
    @Deprecated
    @GetMapping(value = ICommonUserController.PATH_GET_USERS_BY_SORT)
    public ResponseEntity<List<USER>> getAllUsersByPath(
            @PathVariable(ICommonUserController.PATH_VARIABLE_SORT)
            String sort,
            HttpSession session);
}
