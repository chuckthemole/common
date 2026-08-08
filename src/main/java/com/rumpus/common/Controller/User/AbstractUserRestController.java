package com.rumpus.common.Controller.User;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.rumpus.common.Builder.LogBuilder;
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
import com.rumpus.common.util.StringUtil;
import com.rumpus.common.views.Template.IUserTemplate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Base class for REST controllers responsible for user management.
 *
 * <p>
 * This class provides the common infrastructure required by REST endpoints that
 * operate on users. It exposes shared dependencies such as the user service,
 * authentication provider, and view template while allowing concrete
 * implementations to define application-specific endpoints.
 * </p>
 *
 * <p>
 * General REST functionality belongs in {@link AbstractCommonRestController}.
 * User-specific behavior should be implemented here or in subclasses.
 * </p>
 *
 * @param <USER>
 *            concrete user model
 * @param <USER_META>
 *            metadata associated with the user
 * @param <USER_SERVICE>
 *            service responsible for user business logic
 * @param <USER_TEMPLATE>
 *            template used to render user views
 */
public abstract class AbstractUserRestController<USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends IUserService<USER, USER_META>,
        USER_TEMPLATE extends IUserTemplate<USER, USER_META>>
        extends
            AbstractCommonRestController
        implements
            ICommonUserController<USER, USER_META, USER_SERVICE, USER_TEMPLATE> {

    /**
     * Default sort order used when none is specified by the client.
     */
    public static final Sort DEFAULT_SORT = Sort.USERNAME;

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
    protected AbstractUserRestController(
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
    public ResponseEntity<List<USER>> getAllUsersByPath(@PathVariable("sort")
    String sort, HttpSession session) {
        LOG_THIS("AbstractUserController::getAllUsersByPath()");
        return getAllUsers(Sort.valueOf(sort), null, session);
    }

    @Override
    public ResponseEntity<List<USER>> getAllUsers(

            @RequestParam(value = "sort", defaultValue = "USERNAME", required = false)
            Sort sort,

            @RequestParam(value = "direction", defaultValue = "ASC", required = false)
            SortDirection direction,

            HttpSession session) {

        LOG_THIS("AbstractUserRestController::getAllUsers()");
        final List<USER> allUsers = this.userService.getAllUsers(sort, direction);
        return ResponseEntity.ok(allUsers);
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
            CreateUserRoleRequest request) {
        LOG_THIS("AbstractUserController::updateUserRole()");
        // TODO implement persistence layer update
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CommonSession> deleteUser(@RequestBody
    String user, HttpServletRequest request) {
        LOG_THIS("USERRestController POST: /api/delete_user");
        HttpSession session = request.getSession();

        // if user was removed, return session with status delete
        final UUID userId = StringUtil.isQuoted(user)
                ? UUID.fromString(user.substring(1, user.length() - 1))
                : UUID.fromString(user);
        if (this.userService.remove(userId)) {
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
        if (this.userService.update(user.getId(), user) != null) { // if user was updated
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
        final UUID userUUID = UUID.fromString(id);
        USER user = this.userService.getById(userUUID);
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

        // session.setAttribute(
        // "user",
        // this.serializerService.serializeToString(
        // user,
        // null));

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
        com.rumpus.common.ICommon.LOG(AbstractUserRestController.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        com.rumpus.common.ICommon.LOG(AbstractUserRestController.class, level, args);
    }

    /**
     * @brief Check if the current user is authenticated
     *
     *        This endpoint can be used by any app inheriting from
     *        AbstractCommonRestController to verify whether the current user is
     *        logged in.
     *
     * @param authentication
     *            Spring Security Authentication object injected by the framework.
     * @return ResponseEntity<Boolean> representing whether the user is
     *         authenticated.
     */
    @GetMapping(value = "/is_authenticated")
    public ResponseEntity<Boolean> getAuthenticationOfUser(Authentication authentication) {
        LOG("AbstractCommonRestController::getAuthenticationOfUser()");

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();

        return new ResponseEntity<>(isAuthenticated, HttpStatus.ACCEPTED);
    }
}
