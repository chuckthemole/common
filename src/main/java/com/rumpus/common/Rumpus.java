package com.rumpus.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rumpus.common.IO.IRumpusIO;
import com.rumpus.common.IO.RumpusIO;

// @Component
abstract public class Rumpus {
    protected IRumpusIO io = new RumpusIO();
    protected static final Logger LOG = LoggerFactory.getLogger(Rumpus.class);

    public final static String NO_ID = String.valueOf(-1);

    public final static String EMPTY_FIELD = "EMPTY_FIELD";
    public final static String NO_NAME = String.valueOf("NO_NAME");
    public final static String NO_PASS = String.valueOf("NO_PASS");

    public final static int SUCCESS = 1;
    public final static int ERROR = -1;
    public final static int EMPTY = -2;
    public final static int NOT_INITIALIZED = -10;
    public final static int NOT_IMPLEMENTED = -11;

    public final static String NO_TABLE = "NO_TABLE";
    public final static String NO_META_TABLE = "NO_META_TABLE";

    // SQL flags
    public final static String CREATE_USER = "CREATE_USER";
    public final static String DELETE_USER = "DELETE_USER";
    public final static String UPDATE_USER = "UPDATE_USER";
    public final static String USER_EXISTS = "USER_EXISTS";
    public final static String DELETE_GROUP = "DELETE_GROUP";
    public final static String FIND_GROUP = "FIND_GROUP";
    public final static String INSERT_GROUP = "INSERT_GROUP";
    public final static String RENAME_GROUP = "RENAME_GROUP";
    public final static String FIND_ALL_GROUPS = "FIND_ALL_GROUPS";
    public final static String CHANGE_PASSWORD = "CHANGE_PASSWORD";
    public final static String CREATE_AUTHORITY = "CREATE_AUTHORITY";
    public final static String FIND_USERS_IN_GROUP = "FIND_USERS_IN_GROUP";
    public final static String GROUP_AUTHORITIES = "GROUP_AUTHORITIES";
    public final static String DELETE_GROUP_MEMBER = "DELETE_GROUP_MEMBER";
    public final static String INSERT_GROUP_MEMBER = "INSERT_GROUP_MEMBER";
    public final static String DELETE_GROUP_MEMBERS = "DELETE_GROUP_MEMBERS";
    public final static String DELETE_GROUP_AUTHORITY = "DELETE_GROUP_AUTHORITY";
    public final static String INSERT_GROUP_AUTHORITY = "INSERT_GROUP_AUTHORITY";
    public final static String DELETE_USER_AUTHORITIES = "DELETE_USER_AUTHORITIES";
    public final static String DELETE_GROUP_AUTHORITIES = "DELETE_GROUP_AUTHORITIES";

    // SQL stored procedures
    protected final static String GET_USER_BY_ID = "get_user_by_id";

    // User
    public static final String USERNAME = "username";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String USER_META_DATA = "user_meta_data";
    public static final String KEYHOLDER = "keyholder";
    public static final String USER_ID = "user_id";

    // Paths
    protected static final String PATH_INDEX = "/";
    protected static final String PATH_API_USERS = "/api/users";
    protected static final String PATH_VIEW_FOOTER = "/view/footer";
    protected static final String PATH_VIEW_USER_TABLE = "/view/user_table";
    protected static final String PATH_LOGIN = "/login";
    protected static final String PATH_LOGIN_FAILURE = "/api/login_failure";
    protected static final String PATH_LOGOUT = "/logout";

    // Roles
    protected static final String ROLE_USER = "ROLE_USER";
    protected static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    protected static final String ROLE_MANAGER = "ROLE_MANAGER";
    protected static final String ROLE_ADMIN = "ROLE_ADMIN";
    
    // Shorthand Roles
    protected static final String USER = "USER";
    protected static final String EMPLOYEE = "EMPLOYEE";
    protected static final String MANAGER = "MANAGER";
    protected static final String ADMIN = "ADMIN";

    // MISC
    protected static final String UNCHECKED = "unchecked";

    // @Autowired
    // public Rumpus() {
    // io = new RumpusIO();
    // }
}
