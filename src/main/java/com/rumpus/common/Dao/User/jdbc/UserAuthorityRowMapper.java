package com.rumpus.common.Dao.User.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Function;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Dao.jdbc.AbstractJdbcRowMapper;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.User.CommonAuthority;
import com.rumpus.common.User.UserAuthority;
import com.rumpus.common.util.Pair;

public class UserAuthorityRowMapper
        extends AbstractJdbcRowMapper<UserAuthority> {

    static final String USER_AUTHORITY_ID_ROW = "user_authority_id";

    static final String USERNAME_ROW = "username";

    static final String AUTHORITY_ID_ROW = "authority_id";

    static final String AUTHORITY_ROW = "authority";

    static final String CREATED_AT_ROW = "created_at";

    static final String GRANTED_BY_ROW = "granted_by";

    static final String EXPIRES_AT_ROW = "expires_at";

    @Override
    protected Function<Pair<ResultSet, Integer>, UserAuthority> initMapperFunction() {

        return resultSetAndRow -> {

            ResultSet rs = resultSetAndRow.getFirst();

            try {

                final Integer userAuthorityId = rs.getInt(USER_AUTHORITY_ID_ROW);

                final String username = rs.getString(USERNAME_ROW);

                final Integer authorityId = rs.getInt(AUTHORITY_ID_ROW);

                final String authorityName = rs.getString(AUTHORITY_ROW);

                final Timestamp createdAtTimestamp = rs.getTimestamp(CREATED_AT_ROW);

                final Timestamp expiresAtTimestamp = rs.getTimestamp(EXPIRES_AT_ROW);

                final UUID grantedBy = rs.getObject(GRANTED_BY_ROW, UUID.class);

                final CommonAuthority authority = new CommonAuthority(
                        authorityName);
                authority.setId(authorityId);

                final Instant createdAt = createdAtTimestamp.toInstant();

                final Instant expiresAt = expiresAtTimestamp != null
                        ? expiresAtTimestamp.toInstant()
                        : null;

                return new UserAuthority(
                        userAuthorityId,
                        username,
                        authority,
                        createdAt,
                        grantedBy,
                        expiresAt);

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

        return "UserAuthorityRowMapper";
    }
}
