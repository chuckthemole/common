package com.rumpus.common.Controller.User;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rumpus.common.Controller.AbstractCommonRestController;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Service.User.IUserService;
import com.rumpus.common.Session.CommonSession;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserCollection.Sort;
import com.rumpus.common.User.AbstractCommonUserCollection.SortDirection;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.ICommonAuthentication;
import com.rumpus.common.User.Requests.CreateUserRequest;
import com.rumpus.common.User.Requests.CreateUserRoleRequest;
import com.rumpus.common.views.Template.IUserTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

abstract public class AbstractAdminUserRestController<
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends IUserService<USER, USER_META>,
        USER_TEMPLATE extends IUserTemplate<USER, USER_META>>
        extends
            AbstractCommonRestController
        implements
            IAdminUserController<USER, USER_META, USER_SERVICE, USER_TEMPLATE> {

    /**
     * Provides access to the currently authenticated user.
     */
    protected final ICommonAuthentication authentication;

    /**
     * Service responsible for user-related business operations.
     */
    protected final USER_SERVICE userService;

    /**
     * Template responsible for rendering user-related views.
     */
    protected final USER_TEMPLATE userTemplate;

    /**
     * Creates a new base user REST controller.
     *
     * @param basePath
     *            base REST path served by this controller
     * @param userService
     *            user business service
     * @param userTemplate
     *            user view template
     * @param authentication
     *            authentication provider
     */
    protected AbstractAdminUserRestController(
            String basePath,
            USER_SERVICE userService,
            USER_TEMPLATE userTemplate,
            ICommonAuthentication authentication) {

        super(basePath);

        this.userService = userService;
        this.userTemplate = userTemplate;
        this.authentication = authentication;
    }

    @Override
    public ResponseEntity<List<USER>> getAllUsers(Sort sort, SortDirection direction,
            HttpSession session) {
        return ResponseEntity.ok(this.userService.getAllUsers(sort, direction));
    }

    @Override
    public ResponseEntity<CommonSession> createUser(@Valid
    CreateUserRequest request,
            HttpServletRequest servletRequest) {
        LOG_THIS("AbstractUserController::userSubmit()");

        USER user = this.userService.createUser(request);

        this.userService.loginUser(user.getUsername(), request.getPassword(), servletRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CommonSession(servletRequest.getSession()));
    }

    @Override
    public ResponseEntity<CommonSession> updateUser(UUID userId, USER user,
            HttpServletRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID userId, HttpServletRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public ResponseEntity<USER> getUserById(UUID userId, HttpServletRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }

    @Override
    public ResponseEntity<List<String>> getUserRoles(UUID userId) {
        LOG_THIS("AbstractAdminUserController::getUserRoles()");
        List<String> roles = this.userService.getUserRoles(userId);
        return ResponseEntity.ok(roles);
    }

    @Override
    public ResponseEntity<Void> addUserRole(UUID userId, @Valid
    CreateUserRoleRequest request) {
        LOG_THIS("AbstractAdminUserController::addUserRole()");
        this.userService.addUserRole(userId, request.getRole());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeUserRole(UUID userId, String role) {
        LOG_THIS("AbstractAdminUserController::removeUserRole()");
        this.userService.removeUserRole(userId, role);
        return ResponseEntity.ok().build();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    private static void LOG_THIS(String... args) {
        LOG(AbstractAdminUserRestController.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        LOG(AbstractAdminUserRestController.class, level, args);
    }
}
