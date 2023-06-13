package com.rumpus.common;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;

// TODO: create stored procedures to make more use out of this class

/**
 * Wrapper for SimpleJdbc actions (insert, call)
 * 
 * CommonJdbc should have an instance in ApiDB initiated before this.
 */
public class CommonSimpleJdbc<MODEL extends Model<MODEL>> extends RumpusObject {

    private final static String NAME = "JdbcTemplate";
    protected SimpleJdbcInsert insert;
    protected SimpleJdbcCall call;
    protected Map<String, Object> paramaters;

    public CommonSimpleJdbc() {
        super(NAME);
        this.paramaters = new HashMap<>();
        this.insert = new SimpleJdbcInsert(CommonJdbc.jdbcTemplate);
        this.call = new SimpleJdbcCall(CommonJdbc.jdbcTemplate);
    }
    public CommonSimpleJdbc(String table) {
        super(NAME);
        this.paramaters = new HashMap<>();
        this.insert = new SimpleJdbcInsert(CommonJdbc.jdbcTemplate).withTableName(table);
        this.call = new SimpleJdbcCall(CommonJdbc.jdbcTemplate);
    }

    public void setInsertTable(final String table) {
        this.insert.withTableName(table);
    }

    public int onAdd(MODEL model) {
        return this.insert.execute(paramaters);
    }
}
