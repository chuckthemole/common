package com.rumpus.common.Dao.User.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.Function;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Dao.jdbc.AbstractJdbcRowMapper;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.User.CommonAuthority;
import com.rumpus.common.util.Pair;

public class AuthorityRowMapper extends AbstractJdbcRowMapper<CommonAuthority> {

    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    protected Function<Pair<ResultSet, Integer>, CommonAuthority> initMapperFunction() {
        return resultSetAndRow -> {
            ResultSet rs = resultSetAndRow.getFirst();

            try {
                UUID id = UUID.fromString(rs.getString(ID));
                String authority = rs.getString(NAME);

                return new CommonAuthority(id, authority);

            } catch (SQLException e) {
                final String log = LogBuilder
                        .logBuilderFromStackTraceElementArray(
                                e.getMessage(),
                                e.getStackTrace())
                        .toString();

                LOG(LogLevel.ERROR, log);

                return null;
            }
        };
    }

    @Override
    public String toString() {
        return "AuthorityRowMapper{}";
    }
}
