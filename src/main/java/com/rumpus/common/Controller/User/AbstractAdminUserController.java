package com.rumpus.common.Controller.User;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rumpus.common.Controller.AbstractCommonController;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Manager.AbstractServiceManager;
import com.rumpus.common.Service.User.IUserService;
import com.rumpus.common.Session.CommonSession;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserCollection.Sort;
import com.rumpus.common.User.AbstractCommonUserCollection.SortDirection;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.Requests.CreateUserRequest;
import com.rumpus.common.User.Requests.UpdateUserRoleRequest;
import com.rumpus.common.views.Template.IUserTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public class AbstractAdminUserController<SERVICES extends AbstractServiceManager<?>,
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends IUserService<USER, USER_META>,
        USER_TEMPLATE extends IUserTemplate<USER, USER_META>>
        extends
            AbstractCommonController<SERVICES, USER, USER_META, USER_SERVICE, USER_TEMPLATE>
        implements
            IAdminUserController<USER, USER_META, USER_SERVICE, USER_TEMPLATE> {

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
    public ResponseEntity<Void> updateUserRoles(UUID userId, UpdateUserRoleRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserRoles'");
    }

    @Override
    public ResponseEntity<USER> getUserById(UUID userId, HttpServletRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    private static void LOG_THIS(String... args) {
        LOG(AbstractAdminUserController.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        LOG(AbstractAdminUserController.class, level, args);
    }

}
