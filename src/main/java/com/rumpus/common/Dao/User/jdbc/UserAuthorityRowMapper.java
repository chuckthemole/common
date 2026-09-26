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

public class UserAuthorityRowMapper extends AbstractJdbcRowMapper<CommonAuthority> {

    @Override
    protected Function<Pair<ResultSet, Integer>, CommonAuthority> initMapperFunction() {
        return ((Pair<ResultSet, Integer> resultSetAndRow) -> {

            ResultSet rs = resultSetAndRow.getFirst();
            CommonAuthority commonAuthority = null;

            try {
                final String authority = rs.getString("authority");
                final UUID id = UUID.fromString(rs.getString("id"));
                commonAuthority = new CommonAuthority(id, authority);
            } catch (SQLException e) {
                final String log = LogBuilder
                        .logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace())
                        .toString();
                LOG(LogLevel.ERROR, log);
            }

            return commonAuthority;
        });
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

}
