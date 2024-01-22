package com.rumpus.common.Controller;

public interface ICommonController {
    ///////////
    // Paths //
    ///////////

    // Common paths
    public static final String PATH_VIEW = "/view";
    public static final String PATH_REDIRECT = "redirect:/";
    public static final String PATH_LOGOUT = "/logout";
    public static final String PATH_LOGIN = "/login";
    public static final String PATH_ADMIN = "/admin";
    public static final String PATH_LOG_ACTION = "/log_action";
    public static final String PATH_POST_USER_TIME_ZONE = "/user_time_zone";

    public static final String COMMON_REST_API_PATH = "/common/api";

    // Paths for views TODO: if I make a view controller interface that inherits this controller interface, move these there
    // public static final String PATH_FOOTER = "/footer";
    // public static final String PATH_HEADER = "/header";
    // public static final String PATH_USER_TABLE = "/user_table";
    // public static final String PATH_RESOURCES = "/resources";
    // public static final String PATH_RESOURCE_BY_NAME = "/resource/{resource_name}";
    // public static final String PATH_VARIABLE_RESOURCE_BY_NAME = "resource_name";
    // public static final String PATH_TEMPLATE_BY_NAME = "/template/{template_name}";
    // public static final String PATH_USER_TEMPLATE = "/template/user/{template_name}";
    // public static final String PATH_VARIABLE_TEMPLATE_BY_NAME = "template_name";
    // public static final String PATH_VARIABLE_USER_TEMPLATE = "user_id";
    // public static final String PATH_COMPONENT_BY_NAME = "/template/{template_name}/{component_name}";
    // public static final String PATH_VARIABLE_COMPONENT_BY_NAME = "component_name";

    public static final String PATH_INDEX = "/";
    public static final String PATH_API = "/api";
    public static final String PATH_API_USERS = "/api/users";
    public static final String PATH_SERVER_API = "/api/server";
    public static final String PATH_PYCOMMON_API = "/api/pycommon";
    public static final String PATH_SCRAPER = "/scraper";
    public static final String PATH_VIEW_FOOTER = "/view/footer";
    public static final String PATH_VIEW_USER_TABLE = "/view/user_table";
    public static final String PATH_LOGIN_FAILURE = "/api/login_failure";
}
