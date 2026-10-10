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
                final int userAuthorityIdValue = rs.getInt(USER_AUTHORITY_ID_ROW);
                final Integer userAuthorityId = rs.wasNull() ? null : userAuthorityIdValue;

                final String username = rs.getString(USERNAME_ROW);

                final int authorityIdValue = rs.getInt(AUTHORITY_ID_ROW);
                final Integer authorityId = rs.wasNull() ? null : authorityIdValue;

                final String authorityName = rs.getString(AUTHORITY_ROW);

                final Timestamp createdAtTimestamp = rs.getTimestamp(CREATED_AT_ROW);

                final Timestamp expiresAtTimestamp = rs.getTimestamp(EXPIRES_AT_ROW);

                // granted_by is stored as VARCHAR, so parse it explicitly.
                final String grantedByValue = rs.getString(GRANTED_BY_ROW);

                final UUID grantedBy = grantedByValue == null || grantedByValue.isBlank()
                        ? null
                        : UUID.fromString(grantedByValue);

                final CommonAuthority authority = new CommonAuthority(authorityName);

                authority.setId(authorityId);

                // Handle nullable timestamps safely.
                final Instant createdAt = createdAtTimestamp != null
                        ? createdAtTimestamp.toInstant()
                        : null;

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

                // Do not silently return a null model.
                throw new IllegalStateException(
                        "Failed to map user authority row",
                        e);
            }
        };
    }

    @Override
    public String toString() {
        return "UserAuthorityRowMapper";
    }
}
