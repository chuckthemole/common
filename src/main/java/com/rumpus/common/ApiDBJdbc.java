package com.rumpus.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Builder.SQLBuilder;
import com.rumpus.common.util.StringUtil;

// TODO make this class abstract
public class ApiDBJdbc<MODEL extends Model<MODEL>> extends ApiDB<MODEL> {

    private final static String API_NAME = "ApiJdbcTemplate";
    // protected static JdbcTemplate CommonJdbc.jdbcTemplate;
    // static {
    //     CommonJdbc.jdbcTemplate = new JdbcTemplate();
    // }
    protected CommonJdbc jdbc;
    protected UniqueIdManager idManager;
    private static final int DEFAULT_ID_LENGTH = 10;


    public ApiDBJdbc(DataSource dataSource, String table, Mapper<MODEL> mapper) {
        super(API_NAME, table, mapper);
        this.jdbc = CommonJdbc.create();
        this.jdbc.setDataSource(dataSource);
        this.idManager = new UniqueIdManager(DEFAULT_ID_LENGTH); // TODO give ability to construct with different length, or setter.
        // this.mapper = mapper;
        // this.add = add;
    }
    public ApiDBJdbc(DataSource dataSource, String table, Mapper<MODEL> mapper, String apiName) {
        super(apiName, table, mapper);
        this.jdbc = CommonJdbc.create();
        this.jdbc.setDataSource(dataSource);
        this.idManager = new UniqueIdManager(DEFAULT_ID_LENGTH);
        // this.mapper = mapper;
        // this.add = add;
    }

    @Override
    public boolean remove(int id) {
        LOG.info("Jdbc::remove()");
        // TODO: Check dependencies to delete
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ")
            .append(table)
            .append(" WHERE ")
            .append(id)
            .append(" = ?;");
        final String sql = sb.toString();
        return CommonJdbc.jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public boolean remove(String name) {
        LOG.info("Jdbc::remove()");
        // TODO: Check dependencies to delete
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ")
            .append(table)
            .append(" WHERE username");
        // if(StringUtil.isQuoted(name) {
        //     sb.append(name);
        // } else {
        //     sb.append("\"").append(name).append("\"");
        // }
        sb.append(" = ?;");
        final String sql = sb.toString();
        LOG.info("\n" + sql + "\n ? = " + name);
        return CommonJdbc.jdbcTemplate.update(sql, name) > 0;
    }

    @Override
    public MODEL get(int id) {
        LOG.info("Jdbc::get()");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ")
            .append(table)
            .append(" WHERE ")
            .append(id)
            .append(" = ?;");
        final String sql = sb.toString();
        LOG.info(sql);
        return CommonJdbc.jdbcTemplate.queryForObject(sql, mapper, id);
    }

    // TODO this is working for user. Need to abstract 'username' for other objects. Maybe add parameter of constraint.
    @Override
    public MODEL get(String name) {
        LOG.info("Jdbc::get()");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ")
            .append(table)
            .append(" WHERE ");
        // if(StringUtil.isQuoted(name)) {
        //     sb.append(name);
        // } else {
        //     sb.append("\"").append(name).append("\"");
        // }
        sb.append("username");
        sb.append(" = ?;");
        final String sql = sb.toString();
        LOG.info(sql);
        return CommonJdbc.jdbcTemplate.queryForObject(sql, mapper, name);
    }

    @Override
    public List<MODEL> get(Map<String, String> constraints) {
        LOG.info("Jdbc::get()");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ")
            .append(table)
            .append(" WHERE ");
        int count = 0;
        int size = constraints.size();
        for(String key : constraints.keySet()) {
            sb.append(key).append(" = ?");
            if(count == size) {
                sb.append(";");
            } else {
                sb.append(" AND ");
            }
            count++;
        }
        final String sql = sb.toString();
        LOG.info(sql);
        return CommonJdbc.jdbcTemplate.query(sql, mapper, constraints.values());
    }

    @Override
    public List<MODEL> getAll() {
        LOG.info("Jdbc::getAll()");
        SQLBuilder sqlBuilder = new SQLBuilder();
        sqlBuilder.selectAll(this.table);

        // StringBuilder sb = new StringBuilder();
        // sb.append("SELECT * FROM ").append(table).append(";");
        // final String sql = sb.toString();
        LOG.info(sqlBuilder.toString());
        List<MODEL> models = CommonJdbc.jdbcTemplate.query(sqlBuilder.toString(), mapper);
        return models;
    }

    @Override
    public MODEL add(MODEL model) {
        LOG.info("Jdbc::add()");
        LOG.info(model.toString());

        // Build sql
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("INSERT INTO ").append(table).append("(");
        for(Map.Entry<String, String> entry : model.getAttributes().entrySet()) {
            sbSql.append(entry.getKey()).append(",");
        }
        sbSql.deleteCharAt(sbSql.length() - 1);
        sbSql.append(") VALUES(");
        for(Map.Entry<String, String> entry : model.getAttributes().entrySet()) {
            sbSql.append("?,");
        }
        sbSql.deleteCharAt(sbSql.length() - 1);
        sbSql.append(");");
        final String sql = sbSql.toString();
        LOG.info(sql);

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        CommonJdbc.jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return model.getStatement().apply(statement);
        }, keyHolder);
        if(keyHolder.getKey() != null) {
            model.setId(keyHolder.getKey().toString());
        } else {
            model.setId(NO_ID);
        }

        return model;

        // return add.apply(model);
    }

    @Override
    public MODEL onInsert(final MODEL model, final String sql) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder(); // TODO read more about this and see what to do with it. Keeping as member variable in Model. - chuck
        CommonJdbc.jdbcTemplate.update((Connection conn) -> {
            return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }, keyHolder);
        if(keyHolder.getKey() != null) {
            model.setKey(keyHolder);
        } else {
            model.setKey(null);
        }
        return model;
    }

    @Override
    public MODEL update(String key, MODEL newModel, String condition) {
        LOG.info("Jdbc::add()");
        // StringBuilder logBuilder = new StringBuilder();
        // logBuilder.append("Updating '").append(model).append("' of type '").append(newModel.name()).append("''.");
        LogBuilder log = new LogBuilder("Updating '", key, "' of type '", newModel.name(), "''.");
        log.info();

        SQLBuilder sqlBuilder = new SQLBuilder();
        if(condition.isEmpty()) {
            sqlBuilder.update(this.table, newModel.getAttributes());
        } else {
            sqlBuilder.update(this.table, newModel.getAttributes(), condition);
        }

        LOG.info(sqlBuilder.toString());

        MODEL model = this.get(key);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        CommonJdbc.jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sqlBuilder.toString(), Statement.RETURN_GENERATED_KEYS);
            if(!condition.isEmpty()) { // TODO think about how to alter this. We can have more than one under 'condition'. Will work for 'user' right now.
                statement.setString(1, key);
            }
            // return model.getStatement().apply(statement);
            return statement;
        }, keyHolder);
        if(keyHolder.getKey() != null) {
            model.setId(keyHolder.getKey().toString());
        } else {
            model.setId(NO_ID);
        }

        return newModel;
    }

    @Override
    public MODEL update(String key, MODEL newModel, Set<String> columns, String condition) {
        LOG.info("Jdbc::add()");
        // StringBuilder logBuilder = new StringBuilder();
        // logBuilder.append("Updating '").append(model).append("' of type '").append(newModel.name()).append("''.");
        LogBuilder log = new LogBuilder("Updating '", key, "' of type '", newModel.name(), "''.");
        log.info();

        // filter necessary columns into map
        Map<String, String> columnNamesAndValues = new HashMap<>();
        Map<String, String> modelAttributes = newModel.getAttributes();
        columns.forEach(column -> {
            if(modelAttributes.containsKey(column)) {
                columnNamesAndValues.put(column, modelAttributes.get(column));
            }
        });

        SQLBuilder sqlBuilder = new SQLBuilder();
        if(condition.isEmpty()) {
            sqlBuilder.update(this.table, columnNamesAndValues);
        } else {
            sqlBuilder.update(this.table, columnNamesAndValues, condition);
        }

        LOG.info(sqlBuilder.toString());

        MODEL model = this.get(key);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        CommonJdbc.jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sqlBuilder.toString(), Statement.RETURN_GENERATED_KEYS);
            if(!condition.isEmpty()) { // TODO think about how to alter this. We can have more than one under 'condition'. Will work for 'user' right now.
                statement.setString(1, key);
            }
            // return model.getStatement().apply(statement);
            return statement;
        }, keyHolder);
        if(keyHolder.getKey() != null) {
            model.setId(keyHolder.getKey().toString());
        } else {
            model.setId(NO_ID);
        }

        return newModel;
    }

    @Override
    public MODEL update(String model, MODEL newModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean isInitialized() {
        return super.initialized;
    }

    @Override
    public boolean removeAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public Mapper<MODEL> getMapper() {
        return super.mapper;
    }

    // public int setMapper(Mapper<MODEL> mapper) {
    //     this.mapper = mapper;
    //     return SUCCESS;
    // }

    // public int setAddFunc(Function<T, T> add) {
    //     this.add = add;
    //     return SUCCESS;
    // }
}
