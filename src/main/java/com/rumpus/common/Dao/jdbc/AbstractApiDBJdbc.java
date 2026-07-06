package com.rumpus.common.Dao.jdbc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.AbstractDao;
import com.rumpus.common.Dao.TableDefinition;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.Model.AbstractModel;

import org.jooq.Query;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

public abstract class AbstractApiDBJdbc<MODEL extends AbstractModel<MODEL, ?>> extends AbstractDao<MODEL> {

    /**
     * The {@link CommonJdbc} for this Dao
     */
    protected CommonJdbc jdbc;

    /**
     * RowMapper used for converting JDBC ResultSets into model instances.
     */
    protected final RowMapper<MODEL> mapper;

    public AbstractApiDBJdbc(DataSource dataSource, String table, RowMapper<MODEL> mapper) {
        super(TableDefinition.builder().main(table).build());
        this.mapper = mapper;
        this.jdbc = CommonJdbc.createAndSetDataSource(dataSource);
    }

    @Override
    protected boolean doRemove(UUID id) {
        LOG_THIS("remove()");

        final Query query = this.dslContext
                .deleteFrom(this.mainTable())
                .where(DSL.field(ICommon.ID, UUID.class).eq(id));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        final String sql = query.getSQL();
        final List<Object> params = query.getBindValues();
        final int rowsAffected = this.jdbc.update(sql, params.toArray());

        return rowsAffected > 0;
    }

    @Override
    protected Optional<MODEL> doGetById(UUID id) {

        LOG_THIS("getById()");

        final Query query = this.dslContext
                .select()
                .from(mainTable())
                .where(
                        DSL.field(ICommon.ID, UUID.class)
                                .eq(id));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        return this.jdbc.query(
                query.getSQL(),
                this.mapper,
                query.getBindValues().toArray())
                .stream()
                .findFirst();
    }

    @Override
    protected List<MODEL> doGetAll() {

        LOG_THIS("getAll()");

        final Query query = this.dslContext
                .select(DSL.asterisk())
                .from(mainTable());

        LOG_THIS(query.getSQL(ParamType.INLINED));

        final String sql = query.getSQL();
        final Object[] params = query.getBindValues().toArray();

        return this.jdbc.query(sql, this.mapper, params);
    }

    @Override
    protected Page<MODEL> doFindAll(Pageable pageable) {

        LOG_THIS("doFindAll(pageable)");

        final Query query = super.dslContext
                .select()
                .from(this.mainTable())
                .limit(pageable.getPageSize())
                .offset((int) pageable.getOffset());

        LOG_THIS(query.getSQL(ParamType.INLINED));

        List<MODEL> models = this.jdbc.query(
                query.getSQL(),
                this.mapper,
                query.getBindValues().toArray());

        return new org.springframework.data.domain.PageImpl<>(
                models,
                pageable,
                this.count());
    }

    @Override
    protected boolean doRemoveAll() {

        LOG_THIS("removeAll()");

        final Query query = this.dslContext
                .deleteFrom(mainTable());

        LOG_THIS(query.getSQL(ParamType.INLINED));

        final String sql = query.getSQL();
        final Object[] params = query.getBindValues().toArray();

        final int rowsAffected = this.jdbc.update(sql, params);

        LOG_THIS("Rows affected: " + rowsAffected);

        return rowsAffected > 0;
    }

    @Override
    protected long count() {

        LOG_THIS("count()");

        final Query query = this.dslContext
                .selectCount()
                .from(this.mainTable());

        LOG_THIS(query.getSQL(ParamType.INLINED));

        final String sql = query.getSQL();
        final Object[] params = query.getBindValues().toArray();

        Long count = this.jdbc.queryForObject(
                sql,
                Long.class,
                params);

        return count == null ? 0L : count;
    }

    private static void LOG_THIS(String... args) {
        LOG(AbstractApiDBJdbc.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        LOG(AbstractApiDBJdbc.class, level, args);
    }
}
