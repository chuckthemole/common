package com.rumpus.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.annotation.Id;
import org.springframework.jdbc.support.KeyHolder;

public abstract class Model<T extends RumpusObject> extends RumpusObject implements IModel<T> {

    protected static final String NAME = "rawModel";
    @Id protected String id;
    protected KeyHolder key;
    protected Map<String, String> attributes; // TODO: Map<String, String> : String should be abstracted
    protected Function<PreparedStatement, PreparedStatement> statement;
    protected Map<String, String> metaData;

    // Ctors
    public Model(String name) {
        super(name);
        this.attributes = new HashMap<>();
        this.metaData = new HashMap<>();
    }
    public Model(String name, Map<String, String> attributes) {
        super(name);
        this.attributes = attributes;
        this.metaData = new HashMap<>();
    }
    public Model(String name, Map<String, String> attributes, Map<String, String> metaData) {
        super(name);
        this.attributes = attributes;
        this.metaData = metaData;
    }

    @Override
    public int init() {
        return NOT_INITIALIZED;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.attributes.put(ID, id);
        this.id = id;
    }

    @Override
    public KeyHolder getKey() {
        return this.key;
    }

    @Override
    public void setKey(KeyHolder key) {
        // this.attributes.put(ID, id);
        this.id = id;
    }

    @Override
    public void setInitMap(Map<String, String> attributes) {
        this.attributes = attributes;
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
        return attributes;
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
