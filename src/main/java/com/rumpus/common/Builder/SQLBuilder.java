package com.rumpus.common.Builder;

import java.util.Map;

import com.rumpus.common.util.StringUtil;

public class SQLBuilder extends AbstractBuilder {

    private static final String NAME = "SQLBuilder";

    private static final String SELECT = "SELECT";
    private static final String FROM = "FROM";
    private static final String INSERT_INTO = "INSERT INTO";
    private static final String VALUES = "VALUES";
    private static final String UPDATE = "UPDATE";
    private static final String DELETE = "DELETE";
    private static final String WHERE = "WHERE";
    private static final String SET = "SET";
    private static final String STAR = "*";
    private static final String SPACE = " ";
    private static final String RIGHT_PARENTH = ")";
    private static final String LEFT_PARENTH = "(";
    private static final String EQUALS = "=";
    private static final String COMMA = ",";
    private static final String SEMI = ";";

    // private static final String USERNAME = "username";

    private String columnNameValue; // TODO: look below at some usage. This should be set with setter and have default for all be *

    public SQLBuilder() {
        super(NAME);
    }

    public SQLBuilder(String... args) {
        super(NAME, args);
    }

    /**
     * Create sql to select all entries from table
     * @param table to select from
     */
    public void selectAll(final String table) {
        this.builder
            .append(SELECT)
            .append(SPACE)
            .append(STAR)
            .append(SPACE)
            .append(FROM)
            .append(SPACE)
            .append(table)
            .append(SEMI);
    }

    /**
     * 
     * @param table
     * @param constraint
     */
    public void select(final String table, final String constraint) {
        this.builder
            .append(SELECT)
            .append(SPACE)
            .append(STAR)
            .append(SPACE)
            .append(FROM)
            .append(SPACE)
            .append(table)
            .append(SPACE)
            .append(WHERE)
            .append(SPACE)
            .append(SEMI);

    }

    /**
     * 
     * @param table
     * @param username
     */
    public void selectUserByUsername(final String table, final String username) {
        this.builder
            .append(SELECT)
            .append(SPACE)
            .append(STAR)
            .append(SPACE)
            .append(FROM)
            .append(SPACE)
            .append(table)
            .append(SPACE)
            .append(WHERE)
            .append(SPACE)
            .append(USERNAME)
            .append(EQUALS)
            .append(StringUtil.isQuoted(username) ? username : StringUtil.singleQuote(username))
            .append(SEMI);

    }

    /**
     * 
     * @param table
     * @param id
     */
    public void selectById(final String table, final String id) {
        this.builder
            .append(SELECT)
            .append(SPACE)
            .append(STAR)
            .append(SPACE)
            .append(FROM)
            .append(SPACE)
            .append(table)
            .append(SPACE)
            .append(WHERE)
            .append(SPACE)
            .append(ID)
            .append(EQUALS)
            .append(StringUtil.isQuoted(id) ? id : StringUtil.singleQuote(id))
            .append(SPACE)
            .append(SEMI);

    }

    /**
     * DELETE FROM 'table' WHERE username = ?;
     * 
     * @param table user table to use
     * @param username user to delete
     */
    public void deleteByUsername(final String table, final String username) {
        this.builder
            .append(DELETE)
            .append(SPACE)
            .append(FROM)
            .append(SPACE)
            .append(table)
            .append(SPACE)
            .append(WHERE)
            .append(SPACE)
            .append(USERNAME)
            .append(EQUALS)
            .append(StringUtil.isQuoted(username) ? username : StringUtil.singleQuote(username))
            .append(SEMI);

    }

    /**
     * DELETE FROM user WHERE username = ?;
     * 
     * Same as deleteByUsername but for 'user' table specifically
     * 
     * @param username user to delete
     */
    public void deleteUserByUsername(final String username) {
        final String table = "user";
        this.builder
            .append(DELETE)
            .append(SPACE)
            .append(FROM)
            .append(SPACE)
            .append(table)
            .append(SPACE)
            .append(WHERE)
            .append(SPACE)
            .append(USERNAME)
            .append(EQUALS)
            .append(StringUtil.isQuoted(username) ? username : StringUtil.singleQuote(username))
            .append(SEMI);

    }

    /**
     * DELETE FROM 'table' WHERE 'id' = ?;
     * 
     * @param table table to use
     * @param id identifier to delete
     */
    public void deleteById(final String table, final String id) {
        this.builder
            .append(SELECT)
            .append(SPACE)
            .append(STAR)
            .append(SPACE)
            .append(FROM)
            .append(SPACE)
            .append(table)
            .append(SPACE)
            .append(WHERE)
            .append(SPACE)
            .append(ID)
            .append(EQUALS)
            .append(StringUtil.isQuoted(id) ? id : StringUtil.singleQuote(id))
            .append(SPACE)
            .append(SEMI);

    }
    
    /**
     * 
     * @param table
     * @param columnNameValue
     */
    public void insert(final String table, Map<String, String> columnNameValue) {
         if(!columnNameValue.isEmpty()) {
            this.builder
                .append(INSERT_INTO)
                .append(SPACE)
                .append(table)
                .append(SPACE)
                .append(LEFT_PARENTH);

            StringBuilder valuesBuilder = new StringBuilder();
            valuesBuilder.append(LEFT_PARENTH);
            columnNameValue.forEach((column, value) -> {
                this.builder.append(column).append(COMMA);
                if(!StringUtil.isQuoted(value)) {
                    valuesBuilder.append(StringUtil.singleQuote(value));
                } else {
                    valuesBuilder.append(value);
                }
                valuesBuilder.append(COMMA);
            });

            // delete trailing comma
            this.builder.deleteCharAt(this.builder.length() - 1);
            valuesBuilder.deleteCharAt(valuesBuilder.length() - 1);

            valuesBuilder.append(RIGHT_PARENTH);
            this.builder
                .append(RIGHT_PARENTH)
                .append(SPACE)
                .append(VALUES)
                .append(SPACE)
                .append(valuesBuilder.toString())
                .append(SEMI);
         } else {
            LOG.info("Error: No values given to perform an insert.");
         }
    }

    /**
     * 
     * @param table
     * @param columnNameValue
     */
    public void update(String table, Map<String, String> columnNameValue) {
        if(!columnNameValue.isEmpty()) {
            this.builder
                .append(UPDATE)
                .append(SPACE)
                .append(table)
                .append(SPACE)
                .append(SET)
                .append(SPACE);
            columnNameValue.forEach((column, value) -> {
                this.builder.append(column)
                .append(SPACE)
                .append(EQUALS)
                .append(SPACE)
                .append(value)
                .append(COMMA)
                .append(SPACE);
            });

                // delete trailing comma and space
                this.builder.deleteCharAt(this.builder.length() - 1);
                this.builder.deleteCharAt(this.builder.length() - 1);
                this.builder.append(SEMI);
        } else {
            LOG.info("Error: No values given to perform an update.");
        }
    }

    /**
     * Use if you have a condition
     * @param table
     * @param columnNameValue
     * @param where
     */
    public void update(String table, Map<String, String> columnNameValue, String where) {
        if(!columnNameValue.isEmpty()) {
            this.builder
                .append(UPDATE)
                .append(SPACE)
                .append(table)
                .append(SPACE)
                .append(SET)
                .append(SPACE);
                columnNameValue.forEach((column, value) -> {
                    if(!StringUtil.isQuoted(value)) {
                        value = StringUtil.singleQuote(value);
                    }
                    this.builder.append(column)
                    .append(SPACE)
                    .append(EQUALS)
                    .append(SPACE)
                    .append(value)
                    .append(COMMA)
                    .append(SPACE);
                });
                this.builder.deleteCharAt(this.builder.length() - 2); // delete trailing comma
                this.builder.append(SPACE).append(WHERE).append(SPACE).append(where).append(SEMI); // WHERE clause
        } else {
            LOG.info("Error: No values given to perform an update.");
        }
    }
}
