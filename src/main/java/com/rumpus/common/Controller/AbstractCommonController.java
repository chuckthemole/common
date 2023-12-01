package com.rumpus.common.Controller;

import com.rumpus.common.AbstractCommonObject;

public abstract class AbstractCommonController extends AbstractCommonObject {

    private static final String NAME = "CommonController";

    public static final String COMMON_REST_API_PATH = "/common/api";

    // Paths for views
    protected static final String PATH_FOOTER = "/footer";
    protected static final String PATH_HEADER = "/header";
    protected static final String PATH_USER_TABLE = "/user_table";
    protected static final String PATH_RESOURCES = "/resources";
    protected static final String PATH_RESOURCE_BY_NAME = "/resource/{resource_name}";
    protected static final String PATH_VARIABLE_RESOURCE_BY_NAME = "resource_name";
    protected static final String PATH_SECTION_BY_NAME = "/section/{section_name}";
    protected static final String PATH_VARIABLE_SECTION_BY_NAME = "section_name";

    protected String currentBasePath;

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
        this.currentBasePath = AbstractCommonController.COMMON_REST_API_PATH;
    }

    public String getCurrentBasePath() {
        return this.currentBasePath;
    }

    public String setCurrentBasePath(String currentBasePath) {
        return this.currentBasePath = currentBasePath;
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
