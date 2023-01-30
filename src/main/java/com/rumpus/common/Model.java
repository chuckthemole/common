package com.rumpus.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.function.Function;

public class Model<T extends RumpusObject> extends RumpusObject implements IModel<T> {

    protected static final String NAME = "rawModel";
    protected Long id;
    protected Map<String, String> attributeMap; // TODO: Map<String, String> : String should be abstracted
    protected Function<PreparedStatement, PreparedStatement> statement;

    // Ctors
    public Model(String name) {
        super(name);
    }
    public Model(String name, Map<String, String> attributeMap) {
        super(name);
        this.attributeMap = attributeMap;
    }

    @Override
    public int init() {
        return NOT_INITIALIZED;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setInitMap(Map<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }

    @Override
    public void map(ResultSet rs) {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(NAME).append("  id: ").append(id);
        return sb.toString();
    }
    @Override
    public Map<String, String> getAttributes() {
        return attributeMap;
    }
    @Override
    public int setStatement(Function<PreparedStatement, PreparedStatement> statement) {
        this.statement = statement;
        return SUCCESS;
    }
    @Override
    public Function<PreparedStatement, PreparedStatement> getStatement() {
        return this.statement;
    }
}
