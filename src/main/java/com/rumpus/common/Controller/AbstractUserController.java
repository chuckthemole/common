package com.rumpus.common.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Manager.AbstractServiceManager;
import com.rumpus.common.Service.IUserService;
import com.rumpus.common.Session.CommonSession;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserCollection;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.EmptyUserMetaData;
import com.rumpus.common.User.ICommonAuthentication;
import com.rumpus.common.util.StringUtil;
import com.rumpus.common.views.Template.IUserTemplate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

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
        LOG("AbstractUserController::getAllUsersByPath()");
        return getAllUsers(sort, session);
    }

    @Override
    public ResponseEntity<List<USER>> getAllUsers(
            @RequestParam(value = "sort", defaultValue = "", required = false)
            String sort,
            HttpSession session) {

        LOG("AbstractUserController::getAllUsers()");

        final String normalizedSort = sort == null
                ? ""
                : sort.trim().toLowerCase();

        final List<USER> allUsers = this.userService.getAll();

        if (allUsers == null) {

            LOG("Error getting users from user service (null).");

            return new ResponseEntity<>(
                    List.of(),
                    HttpStatus.BAD_REQUEST);
        }

        if (allUsers.isEmpty()) {

            LOG("Error getting users from user service (empty).");

            return new ResponseEntity<>(
                    List.of(),
                    HttpStatus.BAD_REQUEST);
        }

        List<USER> users;

        switch (normalizedSort) {

            case "email" :

                users = AbstractCommonUserCollection
                        .getSortedByEmailListFromCollection(allUsers);

                break;

            case "id" :

                users = AbstractCommonUserCollection
                        .getSortedByIdListFromCollection(allUsers);

                break;

            case "username" :

            case "" :

                users = AbstractCommonUserCollection
                        .getSortedByUsernameListFromCollection(allUsers);

                break;

            default :

                LOG(
                        LogBuilder
                                .logBuilderFromStringArgsNoSpaces(
                                        "Invalid sort provided. Using default sort: ",
                                        DEFAULT_SORT.getSort())
                                .toString());

                users = AbstractCommonUserCollection
                        .getSortedByUsernameListFromCollection(allUsers);

                break;
        }

        return new ResponseEntity<>(
                users,
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonSession> userSubmit(@RequestBody
    USER newUser, HttpServletRequest request) {
        LOG("AbstractUserController::userSubmit()");

        // generate UUID for user
        newUser.setId(java.util.UUID.randomUUID());

        // Check if user already exists
        final String username = newUser.getUsername();
        USER user = this.userService.getByUsername(username); // TODO: maybe ID?
        if (user != null) {
            LOG("User already exists.");
            HttpSession session = request.getSession();
            session.setAttribute("status", "user already exists");
            return new ResponseEntity<>(new CommonSession(session), HttpStatusCode.valueOf(400));

        }

        // User does not exist, create user
        HttpSession session = request.getSession();
        LOG("Creating user: " + newUser.toString());
        newUser.setMetaData(EmptyUserMetaData.createEmptyUserMetaData()); // new MetaData adds
                                                                          // creation time
        user = this.userService.add(newUser);

        // catch error creating user
        if (user == null) {
            LOG("ERROR: User is null.");
            session.setAttribute("status", "error creating user");
            return new ResponseEntity<>(new CommonSession(session), HttpStatusCode.valueOf(400));
        }

        // log in user
        AbstractUserController.currentUserLogin(user, request);
        session.setAttribute("loggedIn", true);

        // Gson gson = new GsonBuilder()
        // .setPrettyPrinting()
        // // .excludeFieldsWithoutExposeAnnotation()
        // .serializeNulls()
        // .disableHtmlEscaping()
        // // .registerTypeAdapter(USER.class, USER.getSerializer())
        // .registerTypeAdapter(AbstractCommonUser.class, user.getTypeAdapter())
        // .create();
        // session.setAttribute("user", gson.toJson(user));

        session.setAttribute("user", this.serializerService.serializeToString(user, null));

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        // @SuppressWarnings("unchecked")
        // List<String> messages = (List<String>)
        // request.getSession().getAttribute("MY_SESSION_MESSAGES");
        // if (messages == null) {
        // messages = new ArrayList<>();
        // request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
        // }
        // messages.add(user.toString());
        // request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);

        ResponseEntity<CommonSession> re = new ResponseEntity<>(new CommonSession(session),
                HttpStatus.CREATED);
        return re;
    }

    @Override
    public ResponseEntity<CommonSession> deleteUser(@RequestBody
    String user, HttpServletRequest request) {
        LOG("USERRestController POST: /api/delete_user");
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
        LOG("USERRestController POST: /api/update_user");
        HttpSession session = request.getSession();
        // this.userService.remove(StringUtil.isQuoted(user) ? user.substring(1,
        // user.length() - 1) : user);
        LogBuilder log = LogBuilder.logBuilderFromStringArgs("Update this user: ", user.toString());
        LOG(log.toString());
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
        LOG("USERRestController::getUserById()");
        USER user = this.userService.getById(id);
        if (user != null) {
            LogBuilder log = LogBuilder.logBuilderFromStringArgs("Retrieved user: ",
                    user.toString());
            LOG(log.toString());
            return new ResponseEntity<USER>(user, HttpStatus.ACCEPTED);
        }
        LogBuilder log = LogBuilder.logBuilderFromStringArgs("User with id '", id,
                "' was not found.");
        LOG(LogLevel.ERROR, log.toString());
        return null;
    }

    @Override
    public ResponseEntity<USER> getCurrentUser(Authentication authentication) {
        LOG("USERRestController::getCurrentUser()");
        if (authentication != null) {
            final String username = authentication.getName();
            final USER user = this.userService.getByUsername(username);
            return new ResponseEntity<USER>(user, HttpStatus.ACCEPTED);
        }
        LOG("No user found in authentication.");
        return null;
    }

    @Override
    public ResponseEntity<String> currentUsername() {
        LOG("USERRestController::currentUsername()");
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

    /**
     * Logs in the current user.
     * <p>
     * This uses
     * {@link jakarta.servlet.http.HttpServletRequest#login(String, String)} to
     * login the user.
     * <p>
     * TODO: look into this more
     *
     * @param <USER>
     * @param <USER_META>
     * @param user
     * @param request
     */
    protected static <USER extends AbstractCommonUser<USER, USER_META>,
            USER_META extends AbstractCommonUserMetaData<USER_META>> void currentUserLogin(
                    USER user, HttpServletRequest request) {
        LOG(AbstractUserController.class, "RumpusController::currentUserLogin()");
        String password = user.getUserPassword();
        String username = user.getUsername();
        try {
            StringBuilder sbLogInfo = new StringBuilder();
            sbLogInfo.append("\nUser log in info:\n")
                    .append("  User Name: ")
                    .append(username).append("\n")
                    .append("  User Password: ")
                    .append(password)
                    .append("\n");
            LOG(AbstractUserController.class, sbLogInfo.toString());
            request.login(username, password);
        } catch (ServletException exception) {
            StringBuilder sbLogInfo = new StringBuilder();
            sbLogInfo.append("\nError with log in request:\n").append("  ")
                    .append(exception.toString()).append("\n");
            LOG(AbstractUserController.class, sbLogInfo.toString());
        }
    }

    protected int debugUser(USER user) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n* * User * * \n");
        sb.append("  User name: ").append(user.getUsername()).append("\n");
        sb.append("  User email: ").append(user.getEmail()).append("\n");
        sb.append("  User password: ").append(user.getPassword()).append("\n");
        LOG(sb.toString());
        return SUCCESS;
    }
}
