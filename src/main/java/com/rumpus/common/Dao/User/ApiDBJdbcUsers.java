package com.rumpus.common.Dao.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.jdbc.AbstractApiDBJdbc;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

import org.jooq.Query;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.springframework.jdbc.core.RowMapper;

public class ApiDBJdbcUsers<USER extends AbstractCommonUser<USER, META>, META extends AbstractCommonUserMetaData<META>>
        extends
        AbstractApiDBJdbc<USER>
        implements
        IUserDao<USER, META> {

    public ApiDBJdbcUsers(
            DataSource dataSource,
            String table,
            RowMapper<USER> mapper) {
        super(dataSource, table, mapper);
    }

    @Override
    public Optional<USER> getByUsername(String username) {
        LOG_THIS("getByUsername(username)");

        final Query query = super.dslContext
                .select(DSL.asterisk())
                .from(this.mainTable())
                .where(
                        DSL.field(ICommon.USERNAME)
                                .eq(username));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        final String sql = query.getSQL();
        final Object[] params = query.getBindValues().toArray();
        final List<USER> users = this.jdbc.query(sql, this.mapper, params);

        if (users.size() == 1) {
            USER user = users.get(0);
            return Optional.of(user);
        } else if (users.size() == 0) {
            LOG_THIS("No user found with username: " + username);
        } else {
            LOG_THIS("More than one user found with username: " + username);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByUsername(String username) {
        LOG_THIS("existsByUsername(username)");

        final Query query = super.dslContext
                .selectOne()
                .from(this.mainTable())
                .where(DSL.field(ICommon.USERNAME).eq(username));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        final List<Integer> rows = this.jdbc.query(
                query.getSQL(),
                (rs, rowNum) -> 1,
                query.getBindValues().toArray());

        return !rows.isEmpty();
    }

    @Override
    public USER doAdd(USER newUser) {

        LOG_THIS("ApiDBJdbcUsers::doAdd()");

        final Query query = this.dslContext
                .insertInto(this.mainTable())
                .columns(
                        DSL.field(ICommon.ID),
                        DSL.field(ICommon.USER_META_DATA),
                        DSL.field(ICommon.EMAIL),
                        DSL.field(ICommon.USERNAME))
                .values(
                        newUser.getId(),
                        newUser.getMetaData(),
                        newUser.getEmail(),
                        newUser.getUsername());

        LOG_THIS(query.getSQL(ParamType.INLINED));

        int rows = this.jdbc.update(
                query.getSQL(),
                query.getBindValues().toArray());

        return rows == 1 ? newUser : null;
    }

    @Override
    public USER doUpdate(UUID id, USER newUser) {

        LOG_THIS("ApiDBJdbcUsers::doUpdate()");

        final Query query = this.dslContext
                .update(this.mainTable())
                .set(DSL.field(ICommon.USER_META_DATA), newUser.getMetaData())
                .set(DSL.field(ICommon.EMAIL), newUser.getEmail())
                .set(DSL.field(ICommon.USERNAME), newUser.getUsername())
                .where(DSL.field(ICommon.ID).eq(id));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        int rows = this.jdbc.update(
                query.getSQL(),
                query.getBindValues().toArray());

        return rows == 1 ? newUser : null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[table=" + this.mainTable().getName() + "]";
    }

    private static void LOG_THIS(String... args) {
        com.rumpus.common.ICommon.LOG(ApiDBJdbcUsers.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        com.rumpus.common.ICommon.LOG(ApiDBJdbcUsers.class, level, args);
    }
}
