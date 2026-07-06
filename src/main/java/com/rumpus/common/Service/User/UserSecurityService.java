package com.rumpus.common.Service.User;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.rumpus.common.ICommon;

/**
 * Thin wrapper around Spring's JdbcUserDetailsManager.
 */
public class UserSecurityService {
    private static final String CREATE_USER_SQL = "insert into users (username, password, enabled) values (?,?,?)";

    private final JdbcUserDetailsManager delegate;

    public UserSecurityService(JdbcUserDetailsManager delegate) {
        this.delegate = delegate;
        // TODO: I believe spring ships with these default queries, so we do not need to
        // set.
        // this.setDefaultQueries(); // TODO: should we have params for ctor for custom,
        // setQueries?
        // this.delegate.setJdbcTemplate(CommonJdbc.getInstance().getJdbcTemplate());
        // todo: should we do this?
    }

    /**
     * Load Spring Security user by username.
     */
    public UserDetails loadUserByUsername(String username) {
        return delegate.loadUserByUsername(username);
    }

    /**
     * Create a new security user (username/password/enabled + roles).
     */
    public void createUser(UserDetails user) {
        delegate.createUser(user);
    }

    /**
     * Update existing security user.
     */
    public void updateUser(UserDetails user) {
        delegate.updateUser(user);
    }

    /**
     * Delete security user.
     */
    public void deleteUser(String username) {
        delegate.deleteUser(username);
    }

    /**
     * Check if a user exists in Spring Security tables.
     */
    public boolean userExists(String username) {
        return delegate.userExists(username);
    }

    private void setQueries(Map<String, String> queries) {
        for (Map.Entry<String, String> entry : queries.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            if (key.equals(ICommon.CREATE_USER)) {
                this.delegate.setCreateUserSql(value);
            } else if (key.equals(ICommon.DELETE_USER)) {
                this.delegate.setDeleteUserSql(value);
            } else if (key.equals(ICommon.UPDATE_USER)) {
                this.delegate.setUpdateUserSql(value);
            } else if (key.equals(ICommon.USER_EXISTS)) {
                this.delegate.setUserExistsSql(value);
            } else if (key.equals(ICommon.DELETE_GROUP)) {
                this.delegate.setDeleteGroupSql(value);
            } else if (key.equals(ICommon.FIND_GROUP)) {
                this.delegate.setFindGroupIdSql(value);
            } else if (key.equals(ICommon.INSERT_GROUP)) {
                this.delegate.setInsertGroupSql(value);
            } else if (key.equals(ICommon.RENAME_GROUP)) {
                this.delegate.setRenameGroupSql(value);
            } else if (key.equals(ICommon.FIND_ALL_GROUPS)) {
                this.delegate.setFindAllGroupsSql(value);
            } else if (key.equals(ICommon.CHANGE_PASSWORD)) {
                this.delegate.setChangePasswordSql(value);
            } else if (key.equals(ICommon.CREATE_AUTHORITY)) {
                this.delegate.setCreateAuthoritySql(value);
            } else if (key.equals(ICommon.FIND_USERS_IN_GROUP)) {
                this.delegate.setFindUsersInGroupSql(value);
            } else if (key.equals(ICommon.GROUP_AUTHORITIES)) {
                this.delegate.setGroupAuthoritiesSql(value);
            } else if (key.equals(ICommon.DELETE_GROUP_MEMBER)) {
                this.delegate.setDeleteGroupMemberSql(value);
            } else if (key.equals(ICommon.INSERT_GROUP_MEMBER)) {
                this.delegate.setInsertGroupMemberSql(value);
            } else if (key.equals(ICommon.DELETE_GROUP_MEMBERS)) {
                this.delegate.setDeleteGroupMembersSql(value);
            } else if (key.equals(ICommon.DELETE_GROUP_AUTHORITY)) {
                this.delegate.setDeleteGroupAuthoritySql(value);
            } else if (key.equals(ICommon.INSERT_GROUP_AUTHORITY)) {
                this.delegate.setInsertGroupAuthoritySql(value);
            } else if (key.equals(ICommon.DELETE_USER_AUTHORITIES)) {
                this.delegate.setDeleteUserAuthoritiesSql(value);
            } else if (key.equals(ICommon.DELETE_GROUP_AUTHORITIES)) {
                this.delegate.setDeleteGroupAuthoritiesSql(value);
            }
        }
    }

    private void setDefaultQueries() {
        this.delegate.setCreateUserSql(CREATE_USER_SQL);
        this.delegate.setDeleteUserSql(JdbcUserDetailsManager.DEF_DELETE_USER_SQL);
        this.delegate.setUpdateUserSql(JdbcUserDetailsManager.DEF_UPDATE_USER_SQL);
        this.delegate.setUserExistsSql(JdbcUserDetailsManager.DEF_USER_EXISTS_SQL);
        this.delegate.setDeleteGroupSql(JdbcUserDetailsManager.DEF_DELETE_GROUP_SQL);
        this.delegate.setFindGroupIdSql(JdbcUserDetailsManager.DEF_FIND_GROUP_ID_SQL);
        this.delegate.setInsertGroupSql(JdbcUserDetailsManager.DEF_INSERT_GROUP_SQL);
        this.delegate.setRenameGroupSql(JdbcUserDetailsManager.DEF_RENAME_GROUP_SQL);
        this.delegate.setFindAllGroupsSql(JdbcUserDetailsManager.DEF_FIND_GROUPS_SQL);
        this.delegate.setChangePasswordSql(JdbcUserDetailsManager.DEF_CHANGE_PASSWORD_SQL);
        this.delegate.setCreateAuthoritySql(JdbcUserDetailsManager.DEF_INSERT_AUTHORITY_SQL);
        this.delegate.setFindUsersInGroupSql(JdbcUserDetailsManager.DEF_FIND_USERS_IN_GROUP_SQL);
        this.delegate.setGroupAuthoritiesSql(JdbcUserDetailsManager.DEF_GROUP_AUTHORITIES_QUERY_SQL);
        this.delegate.setDeleteGroupMemberSql(JdbcUserDetailsManager.DEF_DELETE_GROUP_MEMBER_SQL);
        this.delegate.setInsertGroupMemberSql(JdbcUserDetailsManager.DEF_INSERT_GROUP_MEMBER_SQL);
        this.delegate.setDeleteGroupMembersSql(JdbcUserDetailsManager.DEF_DELETE_GROUP_MEMBERS_SQL);
        this.delegate
                .setDeleteGroupAuthoritySql(JdbcUserDetailsManager.DEF_DELETE_GROUP_AUTHORITY_SQL);
        this.delegate
                .setInsertGroupAuthoritySql(JdbcUserDetailsManager.DEF_INSERT_GROUP_AUTHORITY_SQL);
        this.delegate.setDeleteUserAuthoritiesSql(
                JdbcUserDetailsManager.DEF_DELETE_USER_AUTHORITIES_SQL);
        this.delegate.setDeleteGroupAuthoritiesSql(
                JdbcUserDetailsManager.DEF_DELETE_GROUP_AUTHORITIES_SQL);
    }
}