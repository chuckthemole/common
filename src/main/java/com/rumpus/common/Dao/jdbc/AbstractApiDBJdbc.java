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

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.AbstractDao;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.CommonKeyHolder;

import org.jooq.Query;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

public abstract class AbstractApiDBJdbc<MODEL extends AbstractModel<MODEL, ?>> extends AbstractDao<MODEL> {

    /**
     * The {@link CommonJdbc} for this Dao
     */
    private CommonJdbc jdbc;

    public AbstractApiDBJdbc(String name, DataSource dataSource, String table, RowMapper<MODEL> mapper) {
        super(name, table, "", mapper); // TODO: Leaving metaTable empty for now. think about how to handle in future.
        this.jdbc = CommonJdbc.createAndSetDataSource(dataSource);
    }

    @Override
    public boolean remove(String name) {
        LOG_THIS("remove()");

        // build the query
        final Query query = super.dslContext
            .deleteFrom(
                DSL.table(this.getTable()))
            .where(DSL.field(ICommon.USERNAME).eq(name));

        // for logging
        final ParamType paramType = ParamType.INLINED;
        final String sql = query.getSQL(paramType);
        LOG_THIS(sql);

        // execute the query
        // return query.execute() > 0; // This is using jooq. Keeping this here for reference. I'm using jdbc for now.
        return this.jdbc.update(sql) > 0;
    }

    @Override
    public List<MODEL> getByConstraints(Map<String, String> constraints) {
        LOG_THIS("getByConstraints()");

        // build the query
        final Query query = super.dslContext
            .select(DSL.asterisk()) // TODO: make a const for this
            .from(
                DSL.table(this.getTable())
            )
            .where(
                DSL.field(constraints.keySet().iterator().next())
                .eq(
                    constraints.values().iterator().next()
                )
            );
        
        // for logging
        final ParamType paramType = ParamType.INLINED;
        final String sql = query.getSQL(paramType);
        LOG_THIS(sql);

        // execute the query, returning the result
        return this.jdbc.query(sql, this.mapper);
    }

    @Override
    public List<MODEL> getByColumnValue(String column, String value) {
        LOG_THIS("getByColumnValue()");

        // build the query
        final Query query = super.dslContext
            .select(DSL.asterisk()) // TODO: make a const for this
            .from(
                DSL.table(this.getTable())
            )
            .where(
                DSL.field(column)
                .eq(value)
            );
        
        // for logging
        final ParamType paramType = ParamType.INLINED;
        final String sql = query.getSQL(paramType);
        LOG_THIS(sql);

        // execute the query, returning the result
        return this.jdbc.query(sql, this.mapper);
    }

    @Override
    public MODEL getById(String id) {
        LOG_THIS("getById()");

        // build the query
        final Query query = super.dslContext
            .select(DSL.asterisk()) // TODO: make a const for this
            .from(
                DSL.table(this.getTable())
            )
            .where(
                DSL.field(ICommon.ID)
                .eq(id)
            );
        
        // for logging
        final ParamType paramType = ParamType.INLINED;
        final String sql = query.getSQL(paramType);
        LOG_THIS(sql);

        // execute the query, returning the result
        return this.onGet(sql);
    }

    @Override
    public List<MODEL> getAll() {
        LOG_THIS("getAll()");

        // build the query
        final Query query = super.dslContext
            .select(DSL.asterisk()) // TODO: make a const for this
            .from(
                DSL.table(this.getTable())
            );
        
        // for logging
        final ParamType paramType = ParamType.INLINED;
        final String sql = query.getSQL(paramType);
        LOG_THIS(sql);
        
        return this.jdbc.query(sql, this.mapper);
    }

    @Override
    public void insert(final String sqlInsertStatement, final Map<String, Object> modelMap) {
        LOG_THIS("insert()");
        LOG_THIS(sqlInsertStatement);
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
        // if(keyHolder.getKey() != null) {
        //     model.setKey(keyHolder);
        // } else {
        //     model.setKey(null);
        // }
        
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

        LOG_THIS("onSimpleInsert()");
        final int rowsAffected = this.jdbc.simpleInsert(this.getTable(), parameters);
        LOG_THIS("Rows affected: ", String.valueOf(rowsAffected));
        return model;
    }

    public Map<String, ?> onSelectById(String id) {
        LOG_THIS("onSelectById()");
        return this.jdbc.simpleExecuteCall(Map.of(GET_USER_BY_ID, id));
    }

    @Override
    public MODEL onGet(final String sql) {
        LOG_THIS("onGet()");
        try{
            return this.jdbc.queryForObject(sql, this.mapper);
        } catch(DataAccessException e) {
            LOG_THIS("onGet() DataAccessException: ", e.getMessage());
        }
        return null;
    }

    @Override
    public MODEL onGet(final String sql, final String name) {
        LOG_THIS("onGet()");
        return this.jdbc.queryForObject(sql, this.mapper, name);
    }

    protected int onUpdate(final String sql, final Object... objects) {
        LOG_THIS("onUpdate()");
        return this.jdbc.update(sql, objects);
    }

    @Override
    public boolean removeAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(AbstractApiDBJdbc.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(AbstractApiDBJdbc.class, level, args);
    }
}
