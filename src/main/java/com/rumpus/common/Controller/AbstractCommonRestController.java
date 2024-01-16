package com.rumpus.common.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.rumpus.common.Service.AbstractUserService;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.views.Template.IUserTemplate;

abstract public class AbstractCommonRestController
    <
        /////////////////////////
        // Define generics here//
        /////////////////////////
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends AbstractUserService<USER, USER_META>,
        USER_TEMPLATE extends IUserTemplate<USER, USER_META>
    >
    extends AbstractCommonController
    <
        /////////////////////////
        // Define generics here//
        /////////////////////////
        USER,
        USER_META,
        USER_SERVICE,
        USER_TEMPLATE
    > {

        public static final String COMMON_REST_API_PATH = "/common/api";

        public AbstractCommonRestController(String name) {
                super(name);
        }

        /**
         * @brief Set the default current base path
         * set a default current base path for your controller
         */
        abstract public void setDefaultCurrentBasePath();


        // CRUD - Create, Read, Update, Delete
        /**
         * @brief GET the current base path
         * @return the current base path
         */
        abstract public ResponseEntity<Map<String, String>> currentBasePath(); // TODO: can maybe not use Map in future

        /**
         * 
         * @param authentication
         * @return
         */
        abstract public ResponseEntity<Boolean> getAuthenticationOfUser(Authentication authentication);
}
