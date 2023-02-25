package com.rumpus.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.function.Function;

public interface IModel<T extends IRumpusObject> extends IRumpusObject {
    int init();
    String getId();
    void setId(String id);
    void map(ResultSet rs);
    void setInitMap(Map<String, String> attributeMap);
    Map<String, String> getAttributes();
    int setStatement(Function<PreparedStatement, PreparedStatement> statement);
    Function<PreparedStatement, PreparedStatement> getStatement();
}
