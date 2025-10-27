package com.rumpus.common.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;

import com.rumpus.common.Manager.AbstractServiceManager;
import com.rumpus.common.Service.IUserService;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.views.Template.IUserTemplate;

abstract public class AbstractCommonRestController<
        /////////////////////////
        // Define generics here//
        /////////////////////////
        SERVICES extends AbstractServiceManager<?>, USER extends AbstractCommonUser<USER, USER_META>, USER_META extends AbstractCommonUserMetaData<USER_META>, USER_SERVICE extends IUserService<USER, USER_META>, USER_TEMPLATE extends IUserTemplate<USER, USER_META>>
        extends AbstractCommonController<
                /////////////////////////
                // Define generics here//
                /////////////////////////
                SERVICES, USER, USER_META, USER_SERVICE, USER_TEMPLATE> {

    public static final String COMMON_REST_API_PATH = "/common/api";

    public AbstractCommonRestController() {

    }

    /**
     * @brief Set the default current base path
     *        set a default current base path for your controller
     */
    abstract public void setDefaultCurrentBasePath();

    // CRUD - Create, Read, Update, Delete
    /**
     * @brief GET the current base path
     * @return the current base path
     */
    abstract public ResponseEntity<Map<String, String>> currentBasePath(); // TODO: can maybe not use Map in future

    /**
     * @brief Check if the current user is authenticated
     * 
     *        This endpoint can be used by any app inheriting from
     *        AbstractCommonRestController
     *        to verify whether the current user is logged in.
     * 
     * @param authentication Spring Security Authentication object injected by the
     *                       framework.
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
