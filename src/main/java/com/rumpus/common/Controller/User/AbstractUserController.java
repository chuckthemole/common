package com.rumpus.common.Controller.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Controller.AbstractCommonController;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Manager.AbstractServiceManager;
import com.rumpus.common.Service.User.IUserService;
import com.rumpus.common.Session.CommonSession;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserCollection;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.ICommonAuthentication;
import com.rumpus.common.User.Requests.CreateUserRequest;
import com.rumpus.common.User.Requests.UpdateUserRoleRequest;
import com.rumpus.common.util.StringUtil;
import com.rumpus.common.views.Template.IUserTemplate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

abstract public class AbstractUserController<
        /////////////////////////
        // Define generics here//
        /////////////////////////
        SERVICES extends AbstractServiceManager<?>, // TODO: can we have the wildcard be SERVICE?
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends IUserService<USER, USER_META>,
        USER_TEMPLATE extends IUserTemplate<USER, USER_META>>
        extends
            AbstractCommonController<
                    /////////////////////////
                    // Define generics here//
                    /////////////////////////
                    SERVICES, USER, USER_META, USER_SERVICE, USER_TEMPLATE>
        implements
            ICommonUserController<
                    /////////////////////////
                    // Define generics here//
                    /////////////////////////
                    USER, USER_META, USER_SERVICE, USER_TEMPLATE> {

    private static final AbstractCommonUserCollection.Sort DEFAULT_SORT = AbstractCommonUserCollection.Sort.USERNAME;
    @Autowired
    protected ICommonAuthentication authentication;

    public AbstractUserController() {

    }

    @Override
    public ResponseEntity<List<USER>> getAllUsersByPath(@PathVariable("sort")
    String sort, HttpSession session) {
        LOG_THIS("AbstractUserController::getAllUsersByPath()");
        return getAllUsers(AbstractCommonUserCollection.Sort.valueOf(sort), null, session);
    }

    @Override
    public ResponseEntity<List<USER>> getAllUsers(

            @RequestParam(value = "sort", defaultValue = "USERNAME", required = false)
            AbstractCommonUserCollection.Sort sort,

            @RequestParam(value = "direction", defaultValue = "ASC", required = false)
            AbstractCommonUserCollection.SortDirection direction,

            HttpSession session) {

        LOG_THIS("==================================================");
        LOG_THIS("getAllUsers invoked");
        LOG_THIS("Session ID: " + (session != null ? session.getId() : "NULL"));

        // ---------------------------------------------------------------------
        // Log request parameters (critical for debugging enum binding issues)
        // ---------------------------------------------------------------------
        LOG_THIS("Raw sort param: " + sort);
        LOG_THIS("Raw direction param: " + direction);

        final List<USER> allUsers = this.userService.getAll();

        if (allUsers == null) {

            LOG_THIS("[ERROR] userService.getAll() returned NULL");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of());
        }

        LOG_THIS("Total users fetched: " + allUsers.size());

        if (allUsers.isEmpty()) {

            LOG_THIS("[WARN] No users found in database/service");

            return ResponseEntity.ok(List.of());
        }

        // ---------------------------------------------------------------------
        // Sorting selection debug
        // ---------------------------------------------------------------------
        LOG_THIS("Sorting strategy selected: " + sort);
        LOG_THIS("Sorting direction: " + direction);

        List<USER> users;

        switch (sort) {

            case EMAIL :

                LOG_THIS("Applying sort: EMAIL");

                users = AbstractCommonUserCollection
                        .getSortedByEmailListFromCollection(allUsers);

                break;

            case ID :

                LOG_THIS("Applying sort: ID");

                users = AbstractCommonUserCollection
                        .getSortedByIdListFromCollection(allUsers);

                break;

            case USERNAME :

            default :

                LOG_THIS("Applying sort: USERNAME (default)");

                users = AbstractCommonUserCollection
                        .getSortedByUsernameListFromCollection(allUsers);

                break;
        }

        // ---------------------------------------------------------------------
        // Direction handling
        // ---------------------------------------------------------------------
        if (direction == AbstractCommonUserCollection.SortDirection.DESC) {

            LOG_THIS("Reversing list for DESC order");

            Collections.reverse(users);

        } else {

            LOG_THIS("Keeping ASC order");
        }

        // ---------------------------------------------------------------------
        // Final output log
        // ---------------------------------------------------------------------
        LOG_THIS("Final user count returned: " + users.size());
        LOG_THIS("==================================================");

        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<CommonSession> userSubmit(
            @RequestBody
            CreateUserRequest request,
            HttpServletRequest servletRequest) {

        LOG_THIS("AbstractUserController::userSubmit()");

        USER user = this.userService.createUser(request);

        loginUser(user, request.getPassword(), servletRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CommonSession(servletRequest.getSession()));
    }

    @Override
    public ResponseEntity<Void> updateUserRole(
            @PathVariable
            UUID userId,
            @RequestBody
            UpdateUserRoleRequest request) {
        LOG_THIS("AbstractUserController::updateUserRole()");
        // TODO implement persistence layer update
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CommonSession> deleteUser(@RequestBody
    String user, HttpServletRequest request) {
        LOG_THIS("USERRestController POST: /api/delete_user");
        HttpSession session = request.getSession();
        if (this.userService
                .remove(StringUtil.isQuoted(user) ? user.substring(1, user.length() - 1) : user)) { // if
                                                                                                    // user
                                                                                                    // was
                                                                                                    // removed,
                                                                                                    // return
                                                                                                    // session
                                                                                                    // with
                                                                                                    // status
                                                                                                    // delete
            session.setAttribute("status", "user deleted");
            return new ResponseEntity<CommonSession>(new CommonSession(session),
                    HttpStatus.CREATED);
        }
        session.setAttribute("status", "error deleting user");
        return new ResponseEntity<CommonSession>(new CommonSession(session), HttpStatus.CREATED); // else
                                                                                                  // return
                                                                                                  // session
                                                                                                  // with
                                                                                                  // status
                                                                                                  // error
    }

    @Override
    public ResponseEntity<CommonSession> updateUser(@RequestBody
    USER user, HttpServletRequest request) {
        LOG_THIS("USERRestController POST: /api/update_user");
        HttpSession session = request.getSession();
        // this.userService.remove(StringUtil.isQuoted(user) ? user.substring(1,
        // user.length() - 1) : user);
        LogBuilder log = LogBuilder.logBuilderFromStringArgs("Update this user: ", user.toString());
        LOG_THIS(log.toString());
        if (this.userService.update(user.getId().toString(), user) != null) { // if user was updated
                                                                              // successfully,
                                                                              // return session with
                                                                              // status updateed
            session.setAttribute("status", "user updated");
            return new ResponseEntity<CommonSession>(new CommonSession(session),
                    HttpStatus.CREATED);

        }
        session.setAttribute("status", "error updating user");
        return new ResponseEntity<CommonSession>(new CommonSession(session), HttpStatus.CREATED);
    }

    // TODO this should be secured so user info is not visible
    @Override
    public ResponseEntity<USER> getUserByUsername(
            @PathVariable(ICommonUserController.PATH_VARIABLE_GET_BY_USER_NAME)
            String username,
            HttpServletRequest request) {
        return new ResponseEntity<USER>(this.userService.getByUsername(username),
                HttpStatus.ACCEPTED);
    }

    // TODO this should be secured so user info is not visible
    @Override
    public ResponseEntity<USER> getUserById(
            @PathVariable(ICommonUserController.PATH_VARIABLE_GET_BY_USER_ID)
            String id,
            HttpServletRequest request) {
        LOG_THIS("USERRestController::getUserById()");
        USER user = this.userService.getById(id);
        if (user != null) {
            LogBuilder log = LogBuilder.logBuilderFromStringArgs("Retrieved user: ",
                    user.toString());
            LOG_THIS(log.toString());
            return new ResponseEntity<USER>(user, HttpStatus.ACCEPTED);
        }
        LogBuilder log = LogBuilder.logBuilderFromStringArgs("User with id '", id,
                "' was not found.");
        LOG_THIS(LogLevel.ERROR, log.toString());
        return null;
    }

    @Override
    public ResponseEntity<USER> getCurrentUser(Authentication authentication) {
        LOG_THIS("USERRestController::getCurrentUser()");
        if (authentication != null) {
            final String username = authentication.getName();
            final USER user = this.userService.getByUsername(username);
            return new ResponseEntity<USER>(user, HttpStatus.ACCEPTED);
        }
        LOG_THIS("No user found in authentication.");
        return null;
    }

    @Override
    public ResponseEntity<String> currentUsername() {
        LOG_THIS("USERRestController::currentUsername()");
        final Authentication authentication = this.authentication.getAuthentication();
        if (authentication != null) {
            final String username = authentication.getName();
            return new ResponseEntity<String>(username, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<String>("NO_USER_NAME", HttpStatus.NOT_FOUND);
    }

    /////////////////////////
    // HELPER METHODS //////
    /////////////////////////

    protected void loginUser(
            USER user,
            final String password,
            HttpServletRequest request) {

        String username = user.getUsername();
        try {
            request.login(username, password);
        } catch (ServletException exception) {
            StringBuilder sbLogInfo = new StringBuilder();
            sbLogInfo.append("\nError with log in request:\n").append("  ")
                    .append(exception.toString()).append("\n");
            LOG_THIS(sbLogInfo.toString());
            return;
        }

        HttpSession session = request.getSession();

        session.setAttribute("loggedIn", true);

        session.setAttribute(
                "user",
                this.serializerService.serializeToString(
                        user,
                        null));

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }

    protected int debugUser(USER user) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n* * User * * \n");
        sb.append("  User name: ").append(user.getUsername()).append("\n");
        sb.append("  User email: ").append(user.getEmail()).append("\n");
        sb.append("  User password: ").append(user.getEncodedPassword()).append("\n");
        LOG_THIS(sb.toString());
        return SUCCESS;
    }

    private static void LOG_THIS(String... args) {
        com.rumpus.common.ICommon.LOG(AbstractUserController.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        com.rumpus.common.ICommon.LOG(AbstractUserController.class, level, args);
    }
}
