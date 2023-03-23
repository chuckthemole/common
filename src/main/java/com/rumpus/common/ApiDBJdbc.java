package com.rumpus.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.rumpus.common.util.StringUtil;

public class ApiDBJdbc<MODEL extends Model<MODEL>> extends ApiDB<MODEL> {

    private final static String API_NAME = "ApiJdbcTemplate";
    // protected static JdbcTemplate CommonJdbc.jdbcTemplate;
    // static {
    //     CommonJdbc.jdbcTemplate = new JdbcTemplate();
    // }
    protected CommonJdbc jdbc;

    public ApiDBJdbc(DataSource dataSource, String table, Mapper<MODEL> mapper) {
        super(API_NAME, table, mapper);
        this.jdbc = CommonJdbc.create();
        this.jdbc.setDataSource(dataSource);
        // this.mapper = mapper;
        // this.add = add;
    }
    public ApiDBJdbc(DataSource dataSource, String table, Mapper<MODEL> mapper, String apiName) {
        super(apiName, table, mapper);
        this.jdbc = CommonJdbc.create();
        this.jdbc.setDataSource(dataSource);
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
            .append(" WHERE ");
        if(StringUtil.isQuoted(name)) {
            sb.append(name);
        } else {
            sb.append("\"").append(name).append("\"");
        }
        sb.append(" = ?;");
        final String sql = sb.toString();
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

    @Override
    public MODEL get(String name) {
        LOG.info("Jdbc::get()");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ")
            .append(table)
            .append(" WHERE ");
        if(StringUtil.isQuoted(name)) {
            sb.append(name);
        } else {
            sb.append("\"").append(name).append("\"");
        }
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
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(table).append(";");
        final String sql = sb.toString();
        LOG.info(sql);
        List<MODEL> objects = CommonJdbc.jdbcTemplate.query(sql, mapper);
        return objects;
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
