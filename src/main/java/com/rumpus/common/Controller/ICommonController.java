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

    public static final String PATH_INDEX = "/";
    public static final String PATH_API = "/api";
    public static final String PATH_API_USERS = "/api/users";
    public static final String PATH_SERVER_API = "/api/server";
    public static final String PATH_PYCOMMON_API = "/api/pycommon";
    public static final String PATH_PORTS_API = "/api/ports";
    public static final String PATH_SCRAPER = "/scraper";
    public static final String PATH_VIEW_FOOTER = "/view/footer";
    public static final String PATH_VIEW_USER_TABLE = "/view/user_table";
    public static final String PATH_LOGIN_FAILURE = "/api/login_failure";
}
