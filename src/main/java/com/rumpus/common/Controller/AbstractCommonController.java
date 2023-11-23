package com.rumpus.common.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import com.rumpus.common.AbstractCommonObject;

public abstract class AbstractCommonController extends AbstractCommonObject {

    private static final String NAME = "CommonController";

    public static final String COMMON_REST_API_PATH = "/common/api";

    static protected ICommonPaths commonPaths = null;

    public AbstractCommonController() {
        super(NAME);
        this.init();
    }
    public AbstractCommonController(String name) {
        super(name);
        this.init();
    }

    private void init() {
        if(AbstractCommonController.commonPaths == null) {
            AbstractCommonController.commonPaths = CommonPaths.getInstance();
        }
    }
    
    protected String getWebPage(String uri) {
        return "TODO - getWebPage()";
    }

    // @GetMapping(value = AbstractCommonController.COMMON_REST_API_PATH + "/is_authenticated")
    // static protected ResponseEntity<Boolean> getAuthenticationOfUser(Authentication authentication) {
    //     LOG.info("CommonRestController::getAuthenticationOfUser()");
    //     boolean isAuthenticated = false;
    //     if(authentication != null) {
    //         isAuthenticated = authentication.isAuthenticated();
    //     }
    //     return new ResponseEntity<Boolean>(isAuthenticated, HttpStatus.ACCEPTED);
    // }
}
