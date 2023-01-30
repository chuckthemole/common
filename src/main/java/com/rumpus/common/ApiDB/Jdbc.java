package com.rumpus.common.ApiDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.rumpus.common.Mapper;
import com.rumpus.common.Model;

public class Jdbc<MODEL extends Model<MODEL>> extends Api<MODEL> {

    private final static String API_NAME = "JdbcTemplate";
    protected static JdbcTemplate jdbcTemplate;
    static {
        jdbcTemplate = new JdbcTemplate();
    }

    public Jdbc(DataSource dataSource, String table, Mapper<MODEL> mapper) {
        super(API_NAME, table, mapper);
        jdbcTemplate.setDataSource(dataSource);
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
        return jdbcTemplate.update(sql, id) > 0;
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
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public List<MODEL> getAll() {
        LOG.info("Jdbc::getAll()");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(table).append(";");
        final String sql = sb.toString();
        LOG.info(sql);
        List<MODEL> objects = jdbcTemplate.query(sql, mapper);
        return objects;
    }

    @Override
    public MODEL add(MODEL model) {
        LOG.info("Jdbc::add()");

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

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return model.getStatement().apply(statement);
        }, keyHolder);
        if(keyHolder.getKey() != null) {
            model.setId(keyHolder.getKey().longValue());
        } else {
            model.setId(NO_ID);
        }

        return model;

        // return add.apply(model);
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
