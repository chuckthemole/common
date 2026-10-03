package com.rumpus.common.Dao.User.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Instant;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.TableDefinition;
import com.rumpus.common.Dao.User.IUserAuthorityDao;
import com.rumpus.common.Dao.jdbc.AbstractApiDBJdbc;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.User.CommonAuthority;
import com.rumpus.common.User.UserAuthority;

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
public class UserAuthorityDaoJdbc extends AbstractApiDBJdbc<UserAuthority, Integer>
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
            String userAuthoritiesTable,
            String usersTable,
            String authoritiesDefinitionTable,
            RowMapper<UserAuthority> mapper) {
        super(
                dataSource,
                TableDefinition
                        .builder()
                        .main(userAuthoritiesTable)
                        .table(USERS_TABLE, usersTable)
                        .table(AUTHORITIES_DEFINITION_TABLE, authoritiesDefinitionTable)
                        .build(),
                mapper,
                Integer.class);
    }

    @Override
    public UserAuthority addUserRole(
            String username,
            CommonAuthority role,
            UUID grantedBy,
            Instant expiresAt) {

        LOG_THIS("UserAuthorityDao::addUserRole()");

        /*
         * The user-authority relationship is stored in user_authorities.
         *
         * The caller supplies the username and CommonAuthority, while the
         * user_authorities table stores the authority's database ID rather than
         * its string name. Resolve that ID from the authorities table as part
         * of the INSERT ... SELECT.
         *
         * The caller also supplies the UUID of the user granting the authority
         * and an optional expiration timestamp.
         *
         * created_at is intentionally not included in the INSERT because the
         * database should populate it using DEFAULT CURRENT_TIMESTAMP.
         */

        final Field<String> usernameField = DSL.field("username", String.class);

        final Field<Integer> authorityIdField = DSL.field("authority_id", Integer.class);

        /*
         * This is the ID column from the AUTHORITIES_DEFINITION_TABLE.
         * It is distinct from authorityIdField above, which represents
         * user_authorities.authority_id.
         */
        final Field<Integer> authorityIdSourceField = DSL.field("id", Integer.class);

        final Field<String> authorityField = DSL.field("authority", String.class);

        final Field<UUID> grantedByField = DSL.field("granted_by", UUID.class);

        final Field<Instant> expiresAtField = DSL.field("expires_at", Instant.class);

        final Query query = this.dslContext
                .insertInto(
                        DSL.table(this.mainTable()),
                        usernameField,
                        authorityIdField,
                        grantedByField,
                        expiresAtField)
                .select(
                        DSL.select(
                                DSL.val(username, String.class),
                                authorityIdSourceField,
                                DSL.val(grantedBy, UUID.class),
                                DSL.val(expiresAt, Instant.class))
                                .from(DSL.table(
                                        this.tableDefinition.get(
                                                AUTHORITIES_DEFINITION_TABLE)))
                                .where(authorityField.eq(role.getAuthority())));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        /*
         * Use a KeyHolder so MySQL returns the generated ID from
         * user_authorities.id.
         */
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final int rows = this.jdbc.update(connection -> {

            final PreparedStatement ps = connection.prepareStatement(
                    query.getSQL(),
                    Statement.RETURN_GENERATED_KEYS);

            final Object[] bindValues = query.getBindValues().toArray();

            for (int i = 0; i < bindValues.length; i++) {
                ps.setObject(i + 1, bindValues[i]);
            }

            return ps;

        }, keyHolder);

        /*
         * INSERT ... SELECT returns zero rows when the requested authority
         * does not exist.
         */
        if (rows != 1) {
            throw new IllegalArgumentException(
                    "Unable to add role '" + role.getAuthority()
                            + "' to user '" + username + "'");
        }

        /*
         * The database generated the UserAuthority ID. Retrieve it so we can
         * return the complete UserAuthority represented by the new row.
         */
        final Number generatedId = keyHolder.getKey();

        if (generatedId == null) {
            throw new IllegalStateException(
                    "UserAuthority was inserted, but no generated ID was returned "
                            + "for user '" + username
                            + "' and role '" + role.getAuthority() + "'");
        }

        final Integer userAuthorityId = generatedId.intValue();

        /*
         * Retrieve the complete persisted UserAuthority. This keeps the DAO
         * responsible for constructing the domain object from the database
         * rather than manually reconstructing it here.
         */
        final UserAuthority userAuthority = this.getUserRoleById(userAuthorityId);

        if (userAuthority == null) {
            throw new IllegalStateException(
                    "UserAuthority with ID '" + userAuthorityId
                            + "' was inserted but could not be retrieved");
        }

        return userAuthority;
    }

    @Override
    public void removeUserRole(
            String username,
            CommonAuthority role) {

        LOG_THIS("UserAuthorityDao::removeUserRole()");

        /*
         * User-authority assignments are stored in user_authorities.
         *
         * The user_authorities table stores authority_id rather than the
         * authority name, so resolve the authority definition's ID from
         * the authorities table when performing the delete.
         *
         * If the user has the role, the matching assignment is removed.
         * If the user does not have the role, zero rows are affected and
         * the operation is treated as a no-op.
         */

        final Field<String> usernameField = DSL.field("username", String.class);

        final Field<Integer> authorityIdField = DSL.field("authority_id", Integer.class);

        final Field<Integer> authorityIdSourceField = DSL.field("id", Integer.class);

        final Field<String> authorityField = DSL.field("authority", String.class);

        final Query deleteQuery = this.dslContext
                .deleteFrom(DSL.table(this.mainTable()))
                .where(usernameField.eq(username))
                .and(authorityIdField.in(
                        DSL.select(authorityIdSourceField)
                                .from(DSL.table(
                                        this.tableDefinition.get(
                                                AUTHORITIES_DEFINITION_TABLE)))
                                .where(authorityField.eq(
                                        role.getAuthority()))));

        LOG_THIS(deleteQuery.getSQL(ParamType.INLINED));

        this.jdbc.update(
                deleteQuery.getSQL(),
                deleteQuery.getBindValues().toArray());
    }

    @Override
    public UserAuthority getUserRole(
            String username,
            CommonAuthority role) {

        LOG_THIS("UserAuthorityDao::getUserRole()");

        /*
         * User-authority assignments are stored in user_authorities while the
         * authority definition is stored in authorities. Join the two tables
         * using user_authorities.authority_id = authorities.id.
         *
         * The username and authority name together identify the requested
         * assignment.
         */

        final Field<Integer> userAuthorityIdField = DSL.field(
                DSL.name("ua", ICommon.ID),
                Integer.class)
                .as("user_authority_id");

        final Field<String> usernameField = DSL.field(
                DSL.name("ua", "username"),
                String.class)
                .as("username");

        final Field<Integer> authorityIdField = DSL.field(
                DSL.name("a", ICommon.ID),
                Integer.class)
                .as("authority_id");

        final Field<String> authorityField = DSL.field(
                DSL.name("a", "authority"),
                String.class)
                .as("authority");

        final Field<Instant> createdAtField = DSL.field(
                DSL.name("ua", "created_at"),
                Instant.class)
                .as("created_at");

        final Field<UUID> grantedByField = DSL.field(
                DSL.name("ua", "granted_by"),
                UUID.class)
                .as("granted_by");

        final Field<Instant> expiresAtField = DSL.field(
                DSL.name("ua", "expires_at"),
                Instant.class)
                .as("expires_at");

        final Field<String> userAuthorityUsernameField = DSL.field(
                DSL.name("ua", "username"),
                String.class);

        final Field<Integer> userAuthorityAuthorityIdField = DSL.field(
                DSL.name("ua", "authority_id"),
                Integer.class);

        final Field<Integer> authorityDefinitionIdField = DSL.field(
                DSL.name("a", ICommon.ID),
                Integer.class);

        final Field<String> authorityNameField = DSL.field(
                DSL.name("a", "authority"),
                String.class);

        final Select<?> query = this.dslContext
                .select(
                        userAuthorityIdField,
                        usernameField,
                        authorityIdField,
                        authorityField,
                        createdAtField,
                        grantedByField,
                        expiresAtField)
                .from(
                        DSL.table(this.mainTable()).as("ua"))
                .join(
                        DSL.table(
                                this.tableDefinition.get(
                                        AUTHORITIES_DEFINITION_TABLE))
                                .as("a"))
                .on(userAuthorityAuthorityIdField.eq(
                        authorityDefinitionIdField))
                .where(userAuthorityUsernameField.eq(username))
                .and(authorityNameField.eq(role.getAuthority()));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        return this.jdbc.query(
                query.getSQL(),
                this.mapper,
                query.getBindValues().toArray())
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserAuthority getUserRoleById(
            Integer userAuthorityId) {

        LOG_THIS("UserAuthorityDao::getUserRoleById()");

        /*
         * Retrieve the complete user-authority assignment by its database ID.
         *
         * The authority definition is joined because UserAuthority contains a
         * CommonAuthority object, and the row mapper expects both the assignment
         * columns and the authority definition columns.
         */

        final Field<Integer> userAuthorityIdField = DSL.field(
                DSL.name("ua", ICommon.ID),
                Integer.class)
                .as("user_authority_id");

        final Field<String> usernameField = DSL.field(
                DSL.name("ua", "username"),
                String.class)
                .as("username");

        final Field<Integer> authorityIdField = DSL.field(
                DSL.name("a", ICommon.ID),
                Integer.class)
                .as("authority_id");

        final Field<String> authorityField = DSL.field(
                DSL.name("a", "authority"),
                String.class)
                .as("authority");

        final Field<Instant> createdAtField = DSL.field(
                DSL.name("ua", "created_at"),
                Instant.class)
                .as("created_at");

        final Field<UUID> grantedByField = DSL.field(
                DSL.name("ua", "granted_by"),
                UUID.class)
                .as("granted_by");

        final Field<Instant> expiresAtField = DSL.field(
                DSL.name("ua", "expires_at"),
                Instant.class)
                .as("expires_at");

        final Field<Integer> authorityDefinitionIdField = DSL.field(
                DSL.name("a", ICommon.ID),
                Integer.class);

        final Field<Integer> userAuthorityIdConditionField = DSL.field(
                DSL.name("ua", ICommon.ID),
                Integer.class);

        final Select<?> query = this.dslContext
                .select(
                        userAuthorityIdField,
                        usernameField,
                        authorityIdField,
                        authorityField,
                        createdAtField,
                        grantedByField,
                        expiresAtField)
                .from(
                        DSL.table(this.mainTable()).as("ua"))
                .join(
                        DSL.table(
                                this.tableDefinition.get(
                                        AUTHORITIES_DEFINITION_TABLE))
                                .as("a"))
                .on(
                        DSL.field(
                                DSL.name("ua", "authority_id"),
                                Integer.class)
                                .eq(authorityDefinitionIdField))
                .where(
                        userAuthorityIdConditionField.eq(
                                userAuthorityId));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        return this.jdbc.query(
                query.getSQL(),
                this.mapper,
                query.getBindValues().toArray())
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<UserAuthority> getUserRoles(String username) {

        LOG_THIS("UserAuthorityDao::getUserRoles()");

        /*
         * User-authority assignments are stored in user_authorities.
         *
         * The authority definition is stored separately in the authorities
         * table. Join the two tables using user_authorities.authority_id =
         * authorities.id.
         *
         * The username is already supplied by the caller, so there is no need
         * to join the users table.
         */

        final Field<Integer> userAuthorityIdField = DSL.field(
                DSL.name("ua", ICommon.ID),
                Integer.class)
                .as("user_authority_id");

        final Field<String> usernameField = DSL.field(
                DSL.name("ua", "username"),
                String.class)
                .as("username");

        final Field<Integer> authorityIdField = DSL.field(
                DSL.name("a", ICommon.ID),
                Integer.class)
                .as("authority_id");

        final Field<String> authorityField = DSL.field(
                DSL.name("a", "authority"),
                String.class)
                .as("authority");

        final Field<Instant> createdAtField = DSL.field(
                DSL.name("ua", "created_at"),
                Instant.class)
                .as("created_at");

        final Field<UUID> grantedByField = DSL.field(
                DSL.name("ua", "granted_by"),
                UUID.class)
                .as("granted_by");

        final Field<Instant> expiresAtField = DSL.field(
                DSL.name("ua", "expires_at"),
                Instant.class)
                .as("expires_at");

        final Field<String> userAuthorityUsernameField = DSL.field(
                DSL.name("ua", "username"),
                String.class);

        final Field<Integer> userAuthorityAuthorityIdField = DSL.field(
                DSL.name("ua", "authority_id"),
                Integer.class);

        final Field<Integer> authorityDefinitionIdField = DSL.field(
                DSL.name("a", ICommon.ID),
                Integer.class);

        final Select<?> query = this.dslContext
                .select(
                        userAuthorityIdField,
                        usernameField,
                        authorityIdField,
                        authorityField,
                        createdAtField,
                        grantedByField,
                        expiresAtField)
                .from(
                        DSL.table(this.mainTable()).as("ua"))
                .join(
                        DSL.table(
                                this.tableDefinition.get(
                                        AUTHORITIES_DEFINITION_TABLE))
                                .as("a"))
                .on(userAuthorityAuthorityIdField.eq(
                        authorityDefinitionIdField))
                .where(userAuthorityUsernameField.eq(username));

        LOG_THIS(query.getSQL(ParamType.INLINED));

        return this.jdbc.query(
                query.getSQL(),
                this.mapper,
                query.getBindValues().toArray())
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
     * {@link #addUserRole(String, CommonAuthority, UUID, Instant)} and
     * {@link #removeUserRole(String, CommonAuthority)}.
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
     *              authority model that would otherwise be persisted
     * @return never returns; this operation is intentionally unsupported
     * @throws UnsupportedOperationException
     *                                       always, because authorities must be
     *                                       managed through the explicit
     *                                       user-role operations
     */
    @Override
    protected UserAuthority doAdd(UserAuthority model) {
        throw new UnsupportedOperationException(
                "Generic authority creation is not supported; use the user-role operations instead");
    }

    @Override
    public UserAuthority updateUserRole(
            Integer userAuthorityId,
            Instant expiresAt) {

        LOG_THIS("UserAuthorityDao::updateUserRole()");

        /*
         * A UserAuthority represents an assignment of an authority to a user.
         *
         * This operation only updates the mutable expiration metadata for the
         * existing assignment. The user, authority, created_at, and granted_by
         * values remain unchanged.
         *
         * A null expiresAt value means that the assignment should not expire.
         */

        final Field<Integer> idField = DSL.field(ICommon.ID, Integer.class);

        final Field<Instant> expiresAtField = DSL.field("expires_at", Instant.class);

        final Query updateQuery = this.dslContext
                .update(DSL.table(this.mainTable()))
                .set(expiresAtField, expiresAt)
                .where(idField.eq(userAuthorityId));

        LOG_THIS(updateQuery.getSQL(ParamType.INLINED));

        final int rows = this.jdbc.update(
                updateQuery.getSQL(),
                updateQuery.getBindValues().toArray());

        if (rows != 1) {
            throw new IllegalArgumentException(
                    "Unable to update UserAuthority '"
                            + userAuthorityId + "'");
        }

        /*
         * Retrieve the persisted assignment so the caller receives the complete
         * updated UserAuthority rather than having to reconstruct it.
         */
        final UserAuthority userAuthority = this.getUserRoleById(userAuthorityId);

        if (userAuthority == null) {
            throw new IllegalArgumentException("UserAuthority not found: " + userAuthorityId);
        }

        return userAuthority;
    }

    /**
     * Intentionally not implemented.
     *
     * <p>
     * This DAO manages {@link UserAuthority} assignments. Generic CRUD operations
     * inherited from the base DAO are intentionally unsupported because
     * user-authority assignments require explicit role-management semantics.
     * </p>
     *
     * <p>
     * Updating a {@link UserAuthority}, such as changing its expiration timestamp,
     * must be performed through {@link #updateUserRole(Integer, Instant)} rather
     * than this generic update operation. This ensures that assignment-specific
     * behavior remains explicit and cannot be bypassed through the generic DAO
     * interface.
     * </p>
     *
     * <p>
     * Similarly, user-authority assignments should be created and removed through
     * {@link #addUserRole(String, UserAuthority, UUID, Instant)} and
     * {@link #removeUserRole(String, UserAuthority)}.
     * </p>
     *
     * <p>
     * Authority definitions themselves are separate from user-authority
     * assignments. If authority definitions require independent CRUD operations,
     * those operations should be provided by a dedicated authority DAO such as
     * {@link AuthorityDaoJdbc}.
     * </p>
     *
     * @param id
     *              identifier of the {@link UserAuthority} being updated
     * @param model
     *              authority definition containing the attempted changes
     *
     * @return never returns; this operation is intentionally unsupported
     *
     * @throws UnsupportedOperationException
     *                                       always, because generic updates to
     *                                       {@link UserAuthority} are not
     *                                       supported by this DAO
     */
    @Override
    protected UserAuthority doUpdate(
            Integer id,
            UserAuthority model) {

        throw new UnsupportedOperationException(
                "Generic authority updates are not supported; "
                        + "use the authority DAO or explicit user-role operations");
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
