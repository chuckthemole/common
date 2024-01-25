package com.rumpus.common.Controller;

public interface ICommonViewController {
    public static final String PATH_FOOTER = "/footer";
    public static final String PATH_HEADER = "/header";
    public static final String PATH_USER_TABLE = "/user_table";
    public static final String PATH_RESOURCES = "/resources";
    public static final String PATH_RESOURCE_BY_NAME = "/resource/{resource_name}";
    public static final String PATH_VARIABLE_RESOURCE_BY_NAME = "resource_name";
    public static final String PATH_TEMPLATE_BY_NAME = "/template/{template_name}";
    public static final String PATH_USER_TEMPLATE = "/template/user/{user_id}/{template_name}";
    public static final String PATH_VARIABLE_TEMPLATE_BY_NAME = "template_name";
    public static final String PATH_VARIABLE_USER_ID = "user_id";
    public static final String PATH_COMPONENT_BY_NAME = "/template/{template_name}/{component_name}";
    public static final String PATH_VARIABLE_COMPONENT_BY_NAME = "component_name";
}
