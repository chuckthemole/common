package com.rumpus.common.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

abstract public class AbstractCommonRestController extends AbstractCommonController {
    
        private static final String NAME = "CommonRestController";

        public static final String COMMON_REST_API_PATH = "/common/api";
    
        public AbstractCommonRestController() {
            super(NAME);
        }
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
