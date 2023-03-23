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

    public final static String NO_NAME = String.valueOf("NO_NAME");

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

    // User
    protected static final String USERNAME = "username";
    protected static final String ID = "id";
    protected static final String EMAIL = "email";
    protected static final String PASSWORD = "password";
    protected static final String AUTH_ID = "auth_id";
    protected static final String USER_ID = "user_id";

    // @Autowired
    // public Rumpus() {
    //     io = new RumpusIO();
    // }
}
