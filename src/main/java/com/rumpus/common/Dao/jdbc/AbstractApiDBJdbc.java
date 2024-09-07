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
import org.springframework.jdbc.core.RowMapper;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Builder.SQLBuilder;
import com.rumpus.common.Dao.AbstractDao;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.CommonKeyHolder;

public abstract class AbstractApiDBJdbc<MODEL extends AbstractModel<MODEL>> extends AbstractDao<MODEL> {

    /**
     * The {@link CommonJdbc} for this Dao
     */
    protected CommonJdbc jdbc;

    public AbstractApiDBJdbc(String name, DataSource dataSource, String table, RowMapper<MODEL> mapper) {
        super(name, table, "", mapper); // TODO: Leaving metaTable empty for now. think about how to handle in future.
        this.jdbc = CommonJdbc.createAndSetDataSource(dataSource);
    }

    @Override
    public boolean remove(String name) {
        LOG("ApiDBJdbc::remove()");
        // TODO: Check dependencies to delete
        SQLBuilder sqlBuilder = new SQLBuilder();
        sqlBuilder.deleteUserByUsername(name);
        final String sql = sqlBuilder.toString();
        LOG(sql);
        return this.jdbc.update(sql) > 0;
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
        return this.jdbc.query(sql, mapper, constraints.values());
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
        return this.jdbc.query(sql, mapper, value);
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
        List<MODEL> models = this.jdbc.query(sqlBuilder.toString(), this.mapper);
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
        this.jdbc.execute(sqlInsertStatement, modelMap, ps); // TODO: should I return the result of this?
    }

    @Override
    public MODEL onInsert(MODEL model, final String sql) {
        CommonKeyHolder keyHolder = new CommonKeyHolder(); // TODO read more about this and see what to do with it. Keeping as member variable in Model. - chuck
        this.jdbc.update((Connection conn) -> {
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
        final int rowsAffected = this.jdbc.simpleInsert(this.getTable(), parameters);
        LOG("Rows affected: " + rowsAffected);
        return model;
    }

    public Map<String, ?> onSelectById(String id) {
        return this.jdbc.simpleExecuteCall(Map.of(GET_USER_BY_ID, id));
    }

    @Override
    public MODEL onGet(final String sql) {
        LOG("ApiDBApiDBJdbc::onGet()");
        try{
            return this.jdbc.queryForObject(sql, this.mapper);
        } catch(DataAccessException e) {
            LOG("Error retrieving entry from the db. Details below...");
            LOG(e.toString());
            return null;
        }
    }

    @Override
    public MODEL onGet(final String sql, final String name) {
        return this.jdbc.queryForObject(sql, this.mapper, name);
    }

    protected int onUpdate(final String sql, final Object... objects) {
        return this.jdbc.update(sql, objects);
    }

    @Override
    public boolean removeAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }
}
