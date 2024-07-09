package com.rumpus.common.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.rumpus.common.Cloud.Aws.IAwsS3BucketProperties;
import com.rumpus.common.Config.AbstractCommonUserConfig;
import com.rumpus.common.views.ResourceManager;

public abstract class AbstractCommonController
    <
        /////////////////////////
        // Define generics here//
        /////////////////////////
        USER extends com.rumpus.common.User.AbstractCommonUser<USER, USER_META>,
        USER_META extends com.rumpus.common.User.AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends com.rumpus.common.Service.IUserService<USER, USER_META>,
        USER_TEMPLATE extends com.rumpus.common.views.Template.IUserTemplate<USER, USER_META>
    >

    extends com.rumpus.common.AbstractCommonObject implements ICommonController {

        @Autowired protected org.springframework.core.env.Environment environment;
        @Autowired @Qualifier(AbstractCommonUserConfig.USER_SERVICE) protected USER_SERVICE userService;
        @Autowired protected USER_TEMPLATE userTemplate;
        @Autowired protected ResourceLoader resourceLoader;

        protected String currentBasePath;
        protected ResourceManager resourceManager;

        static protected ICommonPaths commonPaths = null;

        public AbstractCommonController(String name) {
                super(name);
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
