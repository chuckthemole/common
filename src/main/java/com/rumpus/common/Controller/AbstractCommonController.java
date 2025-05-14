package com.rumpus.common.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.env.Environment;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Config.AbstractCommonUserConfig;
import com.rumpus.common.Manager.AbstractServiceManager;
import com.rumpus.common.Service.IUserService;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.views.ResourceManager;
import com.rumpus.common.views.Template.IUserTemplate;
import com.rumpus.common.Service.ISerializerService;

public abstract class AbstractCommonController
    <
        /////////////////////////
        // Define generics here//
        /////////////////////////
        SERVICES extends AbstractServiceManager<?>, // TODO: Can we change wildcard to SERVICE? Also, don't seem to be using this much.
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends IUserService<USER, USER_META>,
        USER_TEMPLATE extends IUserTemplate<USER, USER_META>
    >

    extends AbstractCommonObject implements ICommonController {

        @Autowired protected Environment environment;
        @Autowired @Qualifier(AbstractCommonUserConfig.USER_SERVICE) protected USER_SERVICE userService;
        @Autowired protected ISerializerService serializerService;
        @Autowired protected USER_TEMPLATE userTemplate;
        @Autowired protected ResourceLoader resourceLoader;
        @Autowired protected SERVICES serviceManager;

        protected String currentBasePath;
        protected ResourceManager resourceManager;

        static protected ICommonPaths commonPaths = null;

        public AbstractCommonController() {
                
                this.init();
        }

        private void init() {
            if(AbstractCommonController.commonPaths == null) {
                AbstractCommonController.commonPaths = CommonPaths.getInstance();
            }
            this.currentBasePath = ICommonController.COMMON_REST_API_PATH;
            this.resourceManager = ResourceManager.createEmptyManager();
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

        // @GetMapping(value = ICommonController.COMMON_REST_API_PATH + "/is_authenticated")
        // static protected ResponseEntity<Boolean> getAuthenticationOfUser(Authentication authentication) {
        //     LOG("CommonRestController::getAuthenticationOfUser()");
        //     boolean isAuthenticated = false;
        //     if(authentication != null) {
        //         isAuthenticated = authentication.isAuthenticated();
        //     }
        //     return new ResponseEntity<Boolean>(isAuthenticated, HttpStatus.ACCEPTED);
        // }
}
