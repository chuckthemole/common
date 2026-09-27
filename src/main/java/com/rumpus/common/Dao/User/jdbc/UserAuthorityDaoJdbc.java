package com.rumpus.common.Dao.User.jdbc;

import java.sql.PreparedStatement;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.jooq.Field;
import org.jooq.Query;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.springframework.jdbc.core.RowMapper;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.TableDefinition;
import com.rumpus.common.Dao.User.IUserAuthorityDao;
import com.rumpus.common.Dao.jdbc.AbstractApiDBJdbc;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.User.CommonAuthority;

/**
 * JDBC implementation of {@link IUserAuthorityDao} for managing authorities
 * assigned to users.
 *
 * <p>
 * This DAO uses the Spring Security {@code users} and {@code authorities}
 * tables while exposing a UUID-based API for user identification. User
 * authorities are managed through the explicit role-assignment methods rather
 * than the generic CRUD operations inherited from {@link AbstractApiDBJdbc}.
 * </p>
 */
public class UserAuthorityDaoJdbc extends AbstractApiDBJdbc<CommonAuthority>
        implements
            IUserAuthorityDao {

    /**
     * The authorities table key used in the table definition.
     */
    public static final String AUTHORITIES_DEFINITION_TABLE = "authorities_definition";

    /**
     * The users table key used in the table definition.
     */
    public static final String USERS_TABLE = "users";

    public UserAuthorityDaoJdbc(
            DataSource dataSource,
            String authoritiesTable,
            String usersTable,
            String authoritiesDefinitionTable,
            RowMapper<CommonAuthority> mapper) {
        super(
                dataSource,
                TableDefinition
                        .builder()
                        .main(authoritiesTable)
                        .table(USERS_TABLE, usersTable)
                        .table(AUTHORITIES_DEFINITION_TABLE, authoritiesDefinitionTable)
                        .build(),
                mapper);
    }

    @Override
    public void addUserRole(UUID userId, CommonAuthority role) {

        LOG_THIS("UserAuthorityDao::addUserRole()");

        /*
         * JdbcUserDetailsManager's authorities table uses username rather than the
         * application's UUID. Resolve the username from the users table while
         * performing the insert so callers can remain UUID-based.
         *
         * The INSERT ... SELECT also ensures that a role cannot be inserted for a user
         * that does not exist.
         */
        final Field<String> usernameField = DSL.field(ICommon.USERNAME, String.class);
        final Field<String> authorityField = DSL.field("authority", String.class);
        final Field<String> idField = DSL.field(ICommon.ID, String.class);

        final Query query = this.dslContext
                .insertInto(
                        DSL.table("authorities"),
                        usernameField,
                        authorityField)
                .select(
                        DSL.select(
                                usernameField,
                                DSL.val(role.getAuthority(), String.class))
                                .from(DSL.table("users"))
                                .where(idField.eq(userId.toString())));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        int rows = this.jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query.getSQL());

            ps.setString(1, role.getAuthority());

            return ps;
        });

        /*
         * INSERT ... SELECT returns zero rows if the user does not exist.
         */
        if (rows != 1) {
            throw new IllegalArgumentException(
                    "Unable to add role '" + role.getAuthority()
                            + "' to user '" + userId + "'");
        }
    }

    @Override
    public void removeUserRole(UUID userId, CommonAuthority role) {

        LOG_THIS("UserAuthorityDao::removeUserRole()");

        /*
         * Verify that the user exists before attempting to remove the authority.
         *
         * The DELETE below cannot distinguish between a nonexistent user and an
         * existing user who simply does not have this authority, because both cases
         * result in zero deleted rows.
         */
        final String username = getUsername(userId);

        /*
         * The user exists, so remove the authority from the Spring Security authorities
         * table.
         */
        final Query deleteQuery = this.dslContext
                .deleteFrom(DSL.table("authorities"))
                .where(DSL.field(ICommon.USERNAME).eq(username))
                .and(DSL.field("authority").eq(role.getAuthority()));

        LOG_THIS(deleteQuery.getSQL(ParamType.INLINED));

        this.jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(deleteQuery.getSQL());

            ps.setString(1, username);
            ps.setString(2, role.getAuthority());

            return ps;
        });
    }

    @Override
    public Set<CommonAuthority> getUserRoles(UUID userId) {

        LOG_THIS("UserAuthorityDao::getUserRoles()");

        final Field<String> authorityNameField = DSL
                .field(DSL.name("a", "authority"), String.class)
                .as("authority_name");

        final Field<String> usernameField = DSL.field(
                DSL.name("a", ICommon.USERNAME), String.class);

        final Field<String> userUsernameField = DSL.field(
                DSL.name("u", ICommon.USERNAME), String.class);

        final Field<String> userIdField = DSL.field(
                DSL.name("u", ICommon.ID), String.class);

        final Field<UUID> authorityIdField = DSL
                .field(DSL.name("ad", ICommon.ID), UUID.class)
                .as("authority_id");

        final Field<String> authorityDefinitionNameField = DSL.field(
                DSL.name("ad", "authority"), String.class);

        final Select<?> query = this.dslContext
                .select(
                        authorityIdField,
                        authorityNameField)
                .from(DSL.table("authorities").as("a"))
                .join(DSL.table("users").as("u"))
                .on(usernameField.eq(userUsernameField))
                .join(DSL.table("authority_definitions").as("ad"))
                .on(authorityNameField.eq(authorityDefinitionNameField))
                .where(userIdField.eq(userId.toString()));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        return this.jdbc.query(
                query.getSQL(),
                (rs, rowNum) -> new CommonAuthority(
                        UUID.fromString(rs.getString("authority_id")),
                        rs.getString("authority_name")))
                .stream()
                .collect(Collectors.toSet());
    }

    /**
     * Intentionally not implemented.
     *
     * <p>
     * This DAO represents user-authority persistence, but authorities themselves
     * are not intended to be created or modified through the generic DAO operations
     * inherited from the base DAO hierarchy. In particular, an authority is not
     * treated as a standalone CRUD resource in this part of the application.
     * Authorities are security concepts that are assigned to users and managed
     * through the explicit user-role operations provided by this DAO, such as
     * {@link #addUserRole(UUID, CommonAuthority)} and
     * {@link #removeUserRole(UUID, CommonAuthority)}.
     * </p>
     *
     * <p>
     * This distinction is intentional because the underlying Spring Security
     * {@code authorities} table is part of the application's security model.
     * Allowing callers to invoke a generic {@code doAdd()} operation would make it
     * possible to create arbitrary authority records without establishing the
     * appropriate relationship to a user. It would also bypass the semantics and
     * validation that belong in the explicit role-assignment operations.
     * </p>
     *
     * <p>
     * User authorities should therefore be added and removed through the dedicated
     * role-management methods rather than through the generic CRUD methods
     * inherited from the DAO abstraction.
     * </p>
     *
     * <p>
     * A future refactoring may introduce a dedicated authority DAO or otherwise
     * separate authority definitions from user-authority assignments. Until that
     * distinction exists in the data model and service layer, these generic CRUD
     * operations are deliberately unsupported.
     * </p>
     *
     * @param model
     *            authority model that would otherwise be persisted
     * @return never returns; this operation is intentionally unsupported
     * @throws UnsupportedOperationException
     *             always, because authorities must be managed through the explicit
     *             user-role operations
     */
    @Override
    protected CommonAuthority doAdd(CommonAuthority model) {
        throw new UnsupportedOperationException(
                "Generic authority creation is not supported; use the user-role operations instead");
    }

    /**
     * Intentionally not implemented.
     *
     * <p>
     * Authorities are not intended to be updated through the generic DAO CRUD
     * interface. The authority associated with a user represents a security
     * assignment, and changing that assignment should be performed through the
     * explicit role-management operations rather than through a generic update.
     * </p>
     *
     * <p>
     * This prevents callers from bypassing the rules and semantics associated with
     * assigning and removing authorities from users. The appropriate operations are
     * {@link #addUserRole(UUID, CommonAuthority)} and
     * {@link #removeUserRole(UUID, CommonAuthority)}.
     * </p>
     *
     * <p>
     * A future refactoring may provide a dedicated DAO for authority definitions if
     * authorities become independently managed entities. Until then, this method is
     * deliberately unsupported. {@link AuthorityDaoJdbc} for a DAO that manages
     * authority definitions, which is separate from user-authority assignments.
     * </p>
     *
     * @param id
     *            identifier associated with the attempted authority update
     * @param model
     *            authority model containing the attempted changes
     * @return never returns; this operation is intentionally unsupported
     * @throws UnsupportedOperationException
     *             always, because generic authority updates are not supported
     */
    @Override
    protected CommonAuthority doUpdate(UUID id, CommonAuthority model) {
        throw new UnsupportedOperationException(
                "Generic authority updates are not supported; use the user-role operations instead");
    }

    private String getUsername(UUID userId) {
        final Field<String> usernameField = DSL.field(ICommon.USERNAME, String.class);

        final Field<String> idField = DSL.field(ICommon.ID, String.class);

        final Select<Record1<String>> query = this.dslContext
                .select(usernameField)
                .from(DSL.table("users"))
                .where(idField.eq(userId.toString()));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        return this.jdbc.query(
                query.getSQL(),
                (rs, rowNum) -> rs.getString(ICommon.USERNAME))
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "User does not exist: " + userId));
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(UserAuthorityDaoJdbc.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(UserAuthorityDaoJdbc.class, level, args);
    }

}
