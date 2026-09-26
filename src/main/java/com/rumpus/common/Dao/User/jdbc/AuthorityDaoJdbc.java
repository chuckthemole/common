package com.rumpus.common.Dao.User.jdbc;

import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import com.rumpus.common.Dao.User.IAuthorityDao;
import com.rumpus.common.Dao.jdbc.AbstractApiDBJdbc;
import com.rumpus.common.User.CommonAuthority;

/**
 * JDBC implementation of {@link IAuthorityDao}.
 *
 * <p>
 * This DAO manages the application's registered security authorities. It does
 * not manage the assignment of authorities to users; that responsibility
 * belongs to the user/security service layer.
 * </p>
 */
public class AuthorityDaoJdbc
        extends
            AbstractApiDBJdbc<CommonAuthority>
        implements
            IAuthorityDao {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";

    public AuthorityDaoJdbc(
            DataSource dataSource,
            String table,
            RowMapper<CommonAuthority> mapper) {

        super(dataSource, table, mapper);
    }

    /**
     * Finds a registered authority by its name.
     *
     * @param roleName
     *            the authority name, for example {@code ROLE_ADMIN}
     * @return the matching authority, or {@link Optional#empty()} if none exists
     */
    @Override
    public Optional<CommonAuthority> findByName(String roleName) {
        final String sql = String.format(
                "SELECT %s, %s FROM %s WHERE %s = ?",
                ID_COLUMN,
                NAME_COLUMN,
                this.tables().getMain(),
                NAME_COLUMN);

        return jdbc.query(
                sql,
                this.mapper,
                roleName)
                .stream()
                .findFirst();
    }

    /**
     * Inserts a new registered authority.
     *
     * @param model
     *            the authority to insert
     * @return the inserted authority
     */
    @Override
    protected CommonAuthority doAdd(CommonAuthority model) {
        final String sql = String.format(
                "INSERT INTO %s (%s, %s) VALUES (?, ?)",
                this.tables().getMain(),
                ID_COLUMN,
                NAME_COLUMN);

        jdbc.update(
                connection -> {
                    final PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setObject(1, model.getId());
                    statement.setString(2, model.getAuthority());

                    return statement;
                });

        return model;
    }

    /**
     * Updates an existing registered authority.
     *
     * @param id
     *            the identifier of the authority to update
     * @param model
     *            the new authority values
     * @return the updated authority
     */
    @Override
    protected CommonAuthority doUpdate(UUID id, CommonAuthority model) {
        final String sql = String.format(
                "UPDATE %s SET %s = ? WHERE %s = ?",
                this.tables().getMain(),
                NAME_COLUMN,
                ID_COLUMN);

        jdbc.update(
                sql,
                model.getAuthority(),
                id);

        return new CommonAuthority(id, model.getAuthority());
    }

    /**
     * Returns a string representation of this DAO.
     *
     * @return a description of this DAO and its table
     */
    @Override
    public String toString() {
        return "AuthorityDaoJdbc{" +
                "table='" + this.tables().getMain() + '\'' +
                '}';
    }
}
