package com.rumpus.common.Controller.User;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.rumpus.common.User.Requests.CreateUserRoleRequest;
import com.rumpus.common.views.Template.IUserTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * Admin controller responsible for full user lifecycle management and
 * security-related operations.
 * <p>
 * This controller is restricted to administrative users only. It handles:
 * <ul>
 * <li>User creation (admin-provisioned accounts)</li>
 * <li>User updates</li>
 * <li>User deletion</li>
 * <li>Authority / role management</li>
 * <li>Administrative user lookup</li>
 * </ul>
 * </p>
 */
@PreAuthorize("hasRole('ADMIN')")
public interface IAdminUserController<
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
    String PATH_ADMIN_USER = "/admin/user";
    String PATH_ADMIN_USERS = "/admin/users";
    String PATH_ADMIN_USER_BY_ID = "/admin/users/{userId}";
    String PATH_ADMIN_USER_ROLES = "/admin/users/{userId}/roles";

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
    @GetMapping(value = PATH_ADMIN_USERS)
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
    @PostMapping(value = PATH_ADMIN_USER)
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
    public ResponseEntity<CommonSession> createUser(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Information required to create a new user", required = true)
            @RequestBody
            CreateUserRequest request,
            HttpServletRequest servletRequest);

    /**
     * Update an existing user (admin full update).
     *
     * @param userId
     *            user id
     * @param user
     *            updated user object
     */
    @PutMapping(PATH_ADMIN_USER_BY_ID)
    @Operation(summary = "Admin: Update user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<CommonSession> updateUser(
            @PathVariable
            UUID userId,
            @RequestBody
            USER user,
            HttpServletRequest request);

    /**
     * Delete a user account.
     *
     * @param userId
     *            user id
     */
    @DeleteMapping(PATH_ADMIN_USER_BY_ID)
    @Operation(summary = "Admin: Delete user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<Void> deleteUser(
            @PathVariable
            UUID userId,
            HttpServletRequest request);

    /**
     * Get all roles assigned to a user.
     *
     * @param userId
     *            the user id
     * @return list of roles assigned to the user
     */
    @GetMapping(PATH_ADMIN_USER_ROLES)
    @Operation(summary = "Get user roles", description = "Returns all roles currently assigned to a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<List<String>> getUserRoles(
            @PathVariable
            UUID userId);

    /**
     * Add a role to a user.
     *
     * This operation is idempotent in implementation (adding an existing role
     * should not cause errors).
     *
     * @param userId
     *            target user id
     * @param request
     *            role to assign
     * @return no content on success
     */
    @PostMapping(PATH_ADMIN_USER_ROLES)
    @Operation(summary = "Add role to user", description = "Assigns a new role to the specified user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role added"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid role"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    ResponseEntity<Void> addUserRole(
            @PathVariable
            UUID userId,
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Information required to create a new user role", required = true)
            @RequestBody
            CreateUserRoleRequest request);

    /**
     * Remove a role from a user.
     *
     * @param userId
     *            target user id
     * @param role
     *            role to remove
     * @return no content on success
     */
    @DeleteMapping(PATH_ADMIN_USER_ROLES + "/{role}")
    @Operation(summary = "Remove role from user", description = "Removes a specific role from a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role removed"),
            @ApiResponse(responseCode = "404", description = "User or role not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    ResponseEntity<Void> removeUserRole(
            @PathVariable
            UUID userId,
            @PathVariable
            String role);

    /**
     * Get a user by ID (admin view).
     */
    @GetMapping(PATH_ADMIN_USER_BY_ID)
    @Operation(summary = "Admin: Get user by id")
    ResponseEntity<USER> getUserById(
            @PathVariable
            UUID userId,
            HttpServletRequest request);
}
