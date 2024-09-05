package com.rumpus.common.Dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Builder.SQLBuilder;
import com.rumpus.common.Dao.AbstractDao;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.CommonKeyHolder;

public abstract class AbstractApiDBJdbc<MODEL extends AbstractModel<MODEL>> extends AbstractDao<MODEL> {

    protected CommonJdbc jdbc;
    protected CommonSimpleJdbc<MODEL> simpleJdbc;

    public AbstractApiDBJdbc(String name, DataSource dataSource, String table, Mapper<MODEL> mapper) {
        super(name, table, "", mapper); // TODO: Leaving metaTable empty for now. think about how to handle in future.
        this.jdbc = CommonJdbc.create();
        this.jdbc.setDataSource(dataSource);
        this.simpleJdbc = new CommonSimpleJdbc<>(this.table);
    }

    @Override
    public boolean remove(String name) {
        LOG("ApiDBJdbc::remove()");
        // TODO: Check dependencies to delete
        SQLBuilder sqlBuilder = new SQLBuilder();
        sqlBuilder.deleteUserByUsername(name);
        final String sql = sqlBuilder.toString();
        LOG(sql);
        return CommonJdbc.jdbcTemplate.update(sql) > 0;
    }

    @Override
    public List<MODEL> getByConstraints(Map<String, String> constraints) {
        LOG("ApiDBJdbc::get()");
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
        LOG(sql);
        return CommonJdbc.jdbcTemplate.query(sql, mapper, constraints.values());
    }

    @Override
    public List<MODEL> getByColumnValue(String column, String value) {
        LOG("ApiDBJdbc::getByColumnValue()");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ")
            .append(table)
            .append(" WHERE ")
            .append(column)
            .append(" = ?;");
        final String sql = sb.toString();
        LOG(sql);
        return CommonJdbc.jdbcTemplate.query(sql, mapper, value);
    }

    @Override
    public MODEL getById(String id) {
        SQLBuilder sql = new SQLBuilder();
        sql.selectById(this.table, id);
        return this.onGet(sql.toString());
    }

    @Override
    public List<MODEL> getAll() {
        LOG("ApiDBJdbc::getAll()");
        SQLBuilder sqlBuilder = new SQLBuilder();
        sqlBuilder.selectAll(this.table);

        // StringBuilder sb = new StringBuilder();
        // sb.append("SELECT * FROM ").append(table).append(";");
        // final String sql = sb.toString();
        LOG(sqlBuilder.toString());
        List<MODEL> models = CommonJdbc.jdbcTemplate.query(sqlBuilder.toString(), this.mapper);
        return models;
    }

    @Override
    public void insert(String sqlInsertStatement, Map<String, Object> modelMap) {
        LOG("ApiDBJdbc::insert()");
        LOG(sqlInsertStatement);
        PreparedStatementCallback<Integer> ps = new PreparedStatementCallback<Integer>() {
            public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                return ps.executeUpdate();
            }
        };
        CommonJdbc.namedParameterJdbcTemplate.execute(sqlInsertStatement, modelMap, ps);
    }

    @Override
    public MODEL onInsert(MODEL model, final String sql) {
        CommonKeyHolder keyHolder = new CommonKeyHolder(); // TODO read more about this and see what to do with it. Keeping as member variable in Model. - chuck
        CommonJdbc.jdbcTemplate.update((Connection conn) -> {
            return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }, keyHolder);

        // TODO:i don't think we need to get the key here. 
        if(keyHolder.getKey() != null) {
            model.setKey(keyHolder);
        } else {
            model.setKey(null);
        }

        LogBuilder log = new LogBuilder(true, "KeyHolder debug: ", keyHolder.getKeyAs(String.class));
        log.info();
        
        return model;
    }

    public MODEL onSimpleInsert(MODEL model, Map<String, Object> parameters) {
        //TODO need to look more into keyholder to do this.
        // KeyHolder keyHolder;
        // keyHolder = this.simpleJdbc.insert.executeAndReturnKeyHolder(parameters);
        // if(keyHolder.getKey() != null) {
        //     model.setKey(keyHolder);
        // } else {
        //     model.setKey(null);
        // }
        // return model;

        this.simpleJdbc.insert.execute(parameters);
        return model;
    }

    public Map<String, Object> onSelectById(String id) {
        SqlParameterSource in =  new MapSqlParameterSource().addValue(GET_USER_BY_ID, id);
        return this.simpleJdbc.call.execute(in);
    }

    @Override
    public MODEL onGet(final String sql) {
        LOG("ApiDBApiDBJdbc::onGet()");
        try{
            return CommonJdbc.jdbcTemplate.queryForObject(sql, this.mapper);
        } catch(DataAccessException e) {
            LOG("Error retrieving entry from the db. Details below...");
            LOG(e.toString());
            return null;
        }
    }

    @Override
    public MODEL onGet(final String sql, final String name) {
        return CommonJdbc.jdbcTemplate.queryForObject(sql, this.mapper, name);
    }

    protected int onUpdate(final String sql, final Object... objects) {
        return CommonJdbc.jdbcTemplate.update(sql, objects);
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
}
