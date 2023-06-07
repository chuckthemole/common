package com.rumpus.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.function.Function;

import org.springframework.jdbc.support.KeyHolder;

public interface IModel<T extends IRumpusObject> extends IRumpusObject {
    int init();
    String getId();
    void setId(String id);
    KeyHolder getKey();
    void setKey(KeyHolder key);
    void map(ResultSet rs);
    void setInitMap(Map<String, String> attributeMap);
    Map<String, String> getAttributes();
    int setStatement(Function<PreparedStatement, PreparedStatement> statement);
    Function<PreparedStatement, PreparedStatement> getStatement();
}
