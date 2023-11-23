package com.rumpus.common.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

abstract public class AbstractCommonRestController extends AbstractCommonController {
    
        private static final String NAME = "CommonRestController";

        public static final String COMMON_REST_API_PATH = "/common/api";
    
        public AbstractCommonRestController() {
            super(NAME);
        }
        public AbstractCommonRestController(String name) {
            super(name);
        }
    
        abstract protected ResponseEntity<Boolean> getAuthenticationOfUser(Authentication authentication);
}
