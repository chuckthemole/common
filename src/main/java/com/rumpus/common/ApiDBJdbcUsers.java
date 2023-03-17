package com.rumpus.common;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// TODO: in many of these I'm operating with 'manager' first. Many operations don't return status. Need to figure out how to abort if the manager fails.

/**
 * note: the member manager should be operated on before doing super operations. this is becsuse manager holds the parent table. possibly make the other the parent table. 
 */
public class ApiDBJdbcUsers<USER extends CommonUser<USER>> extends ApiDBJdbc<USER> {

    public static final String CREATE_USER_SQL = "insert into users (username, password, enabled) values (?,?,?)";

    private final static String API_NAME = "CommonJdbcUserManager";
    private final static String USER_MANAGER_TABLE = "USERS";

    private JdbcUserDetailsManager manager;
    private AuthenticationManager authenticationManager;
    private DaoAuthenticationProvider authenticationProvider;

    public ApiDBJdbcUsers(JdbcUserDetailsManager manager, String table, Mapper<USER> mapper) {
        super(manager.getDataSource(), table, mapper, API_NAME);
        this.manager = manager;
        this.manager.setJdbcTemplate(CommonJdbc.jdbcTemplate); // may not need
        // this.manager.setEnableAuthorities(enableAuthorities);
        this.authenticationManager = new CommonAuthManager();
        // this.encoder = new BCryptPasswordEncoder();
        BCryptPasswordEncoder e = new BCryptPasswordEncoder();
        this.authenticationProvider = new DaoAuthenticationProvider();
        this.setDefaultQueries();
    }
    public ApiDBJdbcUsers(JdbcUserDetailsManager manager, String table, Mapper<USER> mapper, Map<String, String> queries) {
        super(manager.getDataSource(), table, mapper, API_NAME);
        this.manager = manager;
        this.manager.setJdbcTemplate(CommonJdbc.jdbcTemplate); // may not need
        // this.manager.setEnableAuthorities(enableAuthorities);
        this.setDefaultQueries();
        if(queries != null && !queries.isEmpty()) {
            this.setQueries(queries);
        }
        this.authenticationManager = new CommonAuthManager();
    }

    @Override
    public boolean remove(int id) {
        LOG.info("JdbcUserManager::remove()");
        // USER user = super.get(id); TODO: need an sql get user name here get user name for below.
        this.manager.deleteUser(name); // look at TODO above
        if(!super.remove(id)) {
            LOG.error("ERROR: ApiDBJdbc.remove() could not remove.");
            return false;
        }
        return true;
    }

    // May not need the super operation if we use ON DELETE CASCADE
    @Override
    public boolean remove(String name) {
        LOG.info("JdbcUserManager::remove()");
        this.manager.deleteUser(name);
        if(!super.remove(name)) {
            LOG.error("ERROR: ApiDBJdbc.remove() could not remove.");
            return false;
        }
        return true;
    }

    @Override
    public USER get(int id) {
        LOG.info("JdbcUserManager::get()");
        USER user = super.get(id);
        if(user == null) {
            LOG.error("ERROR: ApiDBJdbc.get() could not get.");
            return null;
        }
        // TODO implement get user by id for this.manager
        return user;
    }

    @Override
    public USER get(String name) {
        USER user = super.get(name);
        if(user == null) {
            LOG.error("ERROR: ApiDBJdbc.get() could not get.");
            return user;
        }
        // CommonUserDetails details = new CommonUserDetails(this.manager.loadUserByUsername(name));
        UserDetails details = this.manager.loadUserByUsername(name);
        user.setUserDetails(details);
        return user;
    }

    @Override
    public List<USER> get(Map<String, String> constraints) {
        // TODO need to select users from details table too. Maybe use join sql to achieve.

        LOG.info("JdbcUserManager::get()");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ")
            .append(table)
            .append(" WHERE ");
        int count = 0;
        int size = constraints.size();
        for(String key : constraints.keySet()) {
            sb.append(key).append(" = ?");
            if(count == size) {
                sb.append(";");
            } else {
                sb.append(" AND ");
            }
            count++;
        }
        final String sql = sb.toString();
        LOG.info(sql);
        return CommonJdbc.jdbcTemplate.query(sql, mapper, constraints.values());
    }

    @Override
    public List<USER> getAll() {
        LOG.info("JdbcUserManager::getAll()");
        List<USER> users = super.getAll();
        if(users != null && !users.isEmpty()) {
            users.stream().forEach((user) -> {
                // CommonUserDetails details = new CommonUserDetails(this.manager.loadUserByUsername(user.getUsername()));
                user.setUserDetails(this.manager.loadUserByUsername(user.getUsername()));
            });
        }
        return users;
    }

    @Override
    public USER add(USER newUser) {
        LOG.info("JdbcUserManager::add()");
        LOG.info(newUser.toString());
        // CommonUserDetails details = newUser.getUserDetails();
        UserDetails details = newUser.getUserDetails();
        // newUser.setUserDetails(details);
        // this.authenticationManager.authenticate(details.getAuthority()); // messing around with auth manager
        this.manager.createUser(details);

        final String password = newUser.attributes.get(PASSWORD);
        newUser.attributes.remove(PASSWORD);
        // USER user = super.add(newUser);
        if(super.add(newUser) == null) {
            // TODO: maybe catch here
        }
        
        newUser.attributes.put(PASSWORD, password);
        return newUser;
    }

    @Override
    public boolean removeAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    public void setQueriesFromMap(Map<String, String> queries) {
        this.setQueries(queries);
    }

    private void setQueries(Map<String, String> queries) {
        for(Map.Entry<String, String> entry : queries.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            if(key.equals(CREATE_USER)) {
                this.manager.setCreateUserSql(value);
            } else if(key.equals(DELETE_USER)) {
                this.manager.setDeleteUserSql(value);
            } else if(key.equals(UPDATE_USER)) {
                this.manager.setUpdateUserSql(value);
            } else if(key.equals(USER_EXISTS)) {
                this.manager.setUserExistsSql(value);
            } else if(key.equals(DELETE_GROUP)) {
                this.manager.setDeleteGroupSql(value);
            } else if(key.equals(FIND_GROUP)) {
                this.manager.setFindGroupIdSql(value);
            } else if(key.equals(INSERT_GROUP)) {
                this.manager.setInsertGroupSql(value);
            } else if(key.equals(RENAME_GROUP)) {
                this.manager.setRenameGroupSql(value);
            } else if(key.equals(FIND_ALL_GROUPS)) {
                this.manager.setFindAllGroupsSql(value);
            } else if(key.equals(CHANGE_PASSWORD)) {
                this.manager.setChangePasswordSql(value);
            } else if(key.equals(CREATE_AUTHORITY)) {
                this.manager.setCreateAuthoritySql(value);
            } else if(key.equals(FIND_USERS_IN_GROUP)) {
                this.manager.setFindUsersInGroupSql(value);
            } else if(key.equals(GROUP_AUTHORITIES)) {
                this.manager.setGroupAuthoritiesSql(value);
            } else if(key.equals(DELETE_GROUP_MEMBER)) {
                this.manager.setDeleteGroupMemberSql(value);
            } else if(key.equals(INSERT_GROUP_MEMBER)) {
                this.manager.setInsertGroupMemberSql(value);
            } else if(key.equals(DELETE_GROUP_MEMBERS)) {
                this.manager.setDeleteGroupMembersSql(value);
            } else if(key.equals(DELETE_GROUP_AUTHORITY)) {
                this.manager.setDeleteGroupAuthoritySql(value);
            } else if(key.equals(INSERT_GROUP_AUTHORITY)) {
                this.manager.setInsertGroupAuthoritySql(value);
            } else if(key.equals(DELETE_USER_AUTHORITIES)) {
                this.manager.setDeleteUserAuthoritiesSql(value);
            } else if(key.equals(DELETE_GROUP_AUTHORITIES)) {
                this.manager.setDeleteGroupAuthoritiesSql(value);
            }
        }
    }

    private void setDefaultQueries() {
            this.manager.setCreateUserSql(CREATE_USER_SQL);
            this.manager.setDeleteUserSql(JdbcUserDetailsManager.DEF_DELETE_USER_SQL);
            this.manager.setUpdateUserSql(JdbcUserDetailsManager.DEF_UPDATE_USER_SQL);
            this.manager.setUserExistsSql(JdbcUserDetailsManager.DEF_USER_EXISTS_SQL);
            this.manager.setDeleteGroupSql(JdbcUserDetailsManager.DEF_DELETE_GROUP_SQL);
            this.manager.setFindGroupIdSql(JdbcUserDetailsManager.DEF_FIND_GROUP_ID_SQL);
            this.manager.setInsertGroupSql(JdbcUserDetailsManager.DEF_INSERT_GROUP_SQL);
            this.manager.setRenameGroupSql(JdbcUserDetailsManager.DEF_RENAME_GROUP_SQL);
            this.manager.setFindAllGroupsSql(JdbcUserDetailsManager.DEF_FIND_GROUPS_SQL);
            this.manager.setChangePasswordSql(JdbcUserDetailsManager.DEF_CHANGE_PASSWORD_SQL);
            this.manager.setCreateAuthoritySql(JdbcUserDetailsManager.DEF_INSERT_AUTHORITY_SQL);
            this.manager.setFindUsersInGroupSql(JdbcUserDetailsManager.DEF_FIND_USERS_IN_GROUP_SQL);
            this.manager.setGroupAuthoritiesSql(JdbcUserDetailsManager.DEF_GROUP_AUTHORITIES_QUERY_SQL);
            this.manager.setDeleteGroupMemberSql(JdbcUserDetailsManager.DEF_DELETE_GROUP_MEMBER_SQL);
            this.manager.setInsertGroupMemberSql(JdbcUserDetailsManager.DEF_INSERT_GROUP_MEMBER_SQL);
            this.manager.setDeleteGroupMembersSql(JdbcUserDetailsManager.DEF_DELETE_GROUP_MEMBERS_SQL);
            this.manager.setDeleteGroupAuthoritySql(JdbcUserDetailsManager.DEF_DELETE_GROUP_AUTHORITY_SQL);
            this.manager.setInsertGroupAuthoritySql(JdbcUserDetailsManager.DEF_INSERT_GROUP_AUTHORITY_SQL);
            this.manager.setDeleteUserAuthoritiesSql(JdbcUserDetailsManager.DEF_DELETE_USER_AUTHORITIES_SQL);
            this.manager.setDeleteGroupAuthoritiesSql(JdbcUserDetailsManager.DEF_DELETE_GROUP_AUTHORITIES_SQL);
    }
}
