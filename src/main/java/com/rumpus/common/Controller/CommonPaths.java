package com.rumpus.common.Controller;

import java.util.HashMap;
import java.util.Map;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.util.StringUtil;

/**
 * TODO: need to look at this more and see how I'm using it - chuck
 */
public class CommonPaths extends AbstractCommonObject implements ICommonPaths {
    
        private static final String NAME = "CommonPaths";

        private static CommonPaths instance = null;

        private Map<String, Map<String, String>> requestMap;

        // These are some common paths that are used by the common rest controller.
        public static final String CREATE_USER = "CreateUserPath";
        private static final String CREATE_USER_PATH = "/create_user";
        public static final String CURRENT_USER_INFO = "CurrentUserInfoPath";
        private static final String CURRENT_USER_INFO_PATH = "/current_user_info";
    
        private CommonPaths() {
            super(NAME);
            this.init();
        }

        public static synchronized CommonPaths getInstance() {
            if(CommonPaths.instance == null) {
                CommonPaths.instance = new CommonPaths();
            }
            return CommonPaths.instance;
        }

        private void init() {
            this.requestMap = new HashMap<String, Map<String, String>>();
            this.requestMap.put(AbstractCommonController.COMMON_REST_API_PATH, new HashMap<String, String>());
            this.requestMap.get(AbstractCommonController.COMMON_REST_API_PATH).put(CREATE_USER, CREATE_USER_PATH);
            this.requestMap.get(AbstractCommonController.COMMON_REST_API_PATH).put(CURRENT_USER_INFO, CURRENT_USER_INFO_PATH);
        }

        public Map<String, String> getBasePath(String path) {
            return this.requestMap.get(StringUtil.addCharToStartAndOrEnd(path, '/', null));
        }

        @Override
        public String getPath(String basePath, String pathName) {
            if(this.requestMap.containsKey(basePath) == false || this.requestMap.get(basePath).containsKey(pathName) == false) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(basePath).append(this.requestMap.get(basePath).get(pathName));
            return sb.toString();
        }

        @Override
        public void addBasePath(String basePath, Map<String, String> paths, boolean overwrite) {
            if(this.requestMap.containsKey(basePath) == true && overwrite == false) {
                LOG.info("Base path already exists and overwrite is false. Not adding base path.");
                return;
            }
            this.requestMap.put(StringUtil.trimStartAndEnd(basePath, '/'), paths);
        }

        @Override
        public void addPathToBasePath(String basePath, String pathName, String path) {
            if(this.requestMap.containsKey(basePath) == false) {
                LOG.info("Base path does not exist. Not adding path.");
                return;
            }
            this.requestMap.get(basePath).put(pathName, path);
        }

        @Override
        public void removeBasePath(String basePath) {
            if(this.requestMap.remove(basePath) == null) {
                LOG.info("Base path does not exist. Not removing base path.");
            }
        }

        @Override
        public void removePathFromBasePath(String basePath, String pathName) {
            if(this.requestMap.containsKey(basePath) == false) {
                LOG.info("Base path does not exist. Not removing path.");
                return;
            }
            if(this.requestMap.get(basePath).remove(pathName) == null) {
                LOG.info("Path does not exist. Not removing path.");
            }
        }
}
