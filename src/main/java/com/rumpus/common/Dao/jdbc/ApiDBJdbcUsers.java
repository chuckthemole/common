package com.rumpus.common.Dao.jdbc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.rumpus.common.ICommon;
import com.rumpus.common.Blob.AbstractBlob;
import com.rumpus.common.Blob.BlobUtil;
import com.rumpus.common.Blob.IBlob;
import com.rumpus.common.Blob.JdbcBlob;
import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Builder.SQLBuilder;
import com.rumpus.common.Dao.AbstractDao;
import com.rumpus.common.Dao.IUserDao;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.CommonUserDetails;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;

// TODO: in many of these I'm operating with 'manager' first. Many operations don't return status. Need to figure out how to abort if the manager fails.

/**
 * note: the member manager should be operated on before doing super operations. this is becsuse manager holds the parent table. possibly make the other the parent table.
 */
public class ApiDBJdbcUsers
<
    USER extends AbstractCommonUser<USER, META>,
    META extends AbstractCommonUserMetaData<META>
> extends AbstractApiDBJdbc<USER> implements IUserDao<USER, META> {

    public static final String CREATE_USER_SQL = "insert into users (username, password, enabled) values (?,?,?)";
    private static final String UPDATE_USERS_TABLE = "update users set username = ?, password = ?, enabled = ? where username = ?";
    private static final String UPDATE_USER_TABLE = "UPDATE user SET email = ? WHERE id = ?";

    private JdbcUserDetailsManager manager;

    public ApiDBJdbcUsers(
        JdbcUserDetailsManager manager,
        String table,
        RowMapper<USER> mapper) {
            super(manager.getDataSource(), table, mapper);
            this.manager = manager;
            this.manager.setJdbcTemplate(CommonJdbc.getInstance().getJdbcTemplate()); // TODO: may not need this. look into it.
            this.setDefaultQueries();
    }

    public UserDetails getUserDetails(String username) {
        return this.manager.loadUserByUsername(username);
    }

    // May not need the super operation if we use ON DELETE CASCADE
    @Override
    public boolean remove(String name) {
        LOG("ApiDBJdbcUsers::remove()");
        if(!super.remove(name)) {
            LOG(LogLevel.ERROR, "ERROR: ApiDBJdbc.remove() could not remove with name = '", name, "'");
            return false;
        }
        this.manager.deleteUser(name);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LOG("loadUserByUsername(username)");
        return this.manager.loadUserByUsername(username);
    }   

    @Override
    public USER getByUsername(String username) {
        LOG("getByUsername(username)");
        final List <USER> users = this.getByColumnValue(ICommon.USERNAME, username);
        if (users.size() == 1) {
            USER user = users.get(0);
            final UserDetails userDetails = this.loadUserByUsername(username);
            final CommonUserDetails details = CommonUserDetails.createFromUserDetails(userDetails);
            user.setUserDetails(details);
            return user;
        } else if (users.size() == 0) {
            LOG("No user found with username: " + username);
            return null;
        } else {
            LOG("More than one user found with username: " + username);
            return null;
        }
    }

    @Override
    public List<USER> getAll() {
        LOG("ApiDBJdbcUsers::getAll()");
        List<USER> users = super.getAll();
        if(users != null && !users.isEmpty()) {
            users.stream().forEach((user) -> {
                // CommonUserDetails details = new CommonUserDetails(this.manager.loadUserByUsername(user.getUsername()));
                final UserDetails userDetails = this.manager.loadUserByUsername(user.getUsername());
                final CommonUserDetails details = CommonUserDetails.createFromUserDetails(userDetails);
                user.setUserDetails(details);
            });
        } else {
            LOG("Error: returned user list is empty or null.");
        }
        return users;
    }

    @Override
    public USER add(USER newUser) {
        // debug
        LOG("ApiDBJdbcUsers::add()");
        LOG(newUser.toString());

        // check if user exists
        String log = LogBuilder.logBuilderFromStringArgs(
            "- - - Checking if user '",
            newUser.getUsername(),
            "' already exists.").toString();
        LOG(log);
        if(this.getByUsername(newUser.getUsername()) != null) {
            log = LogBuilder.logBuilderFromStringArgs(
                "- - - User with username '",
                newUser.getUsername(),
                "' already exists in the db. Returning null...").toString();
            LOG(log);
            return null;
        }
        if(this.getById(newUser.getId().toString()) != null) {
            log = LogBuilder.logBuilderFromStringArgs(
                "- - - User with id '",
                newUser.getId().toString(),
                "' already exists in the db. Returning null...").toString();
            LOG(log);
            return null;
        }

        // does not exist with id or name given, continue...
        log = LogBuilder.logBuilderFromStringArgs(
            "- - - User with name '",
            newUser.getUsername(),
            "' does not exist, continuing.").toString();
        LOG(log);
        // create user details in manager (user table). this saves username, password, and enabled.
        UserDetails details = newUser.getUserDetails();
        this.manager.createUser(details);

        // TODO check why I'm doing this, then comment. - chuck
        // final String password = newUser.attributes.get(PASSWORD);
        // newUser.attributes.remove(PASSWORD);

        // check if user has an id, if not assign.
        LOG("Checking if user has an id...");
        LOG("User has id: " + newUser.hasId());
        LOG("User id: " + newUser.getId());
        if(!newUser.hasId()) {
            newUser.setId(
                UUID.fromString(
                    AbstractDao
                    .idManager
                    .generateAndReceiveIdForGivenSet(
                        newUser.getClass().getSimpleName())));
        }
        newUser = this.simpleAddUser(newUser);

        // newUser.attributes.put(PASSWORD, password);

        return newUser;
    }

    private USER addUser(USER newUser) {
        // build sql for user meta table
        SQLBuilder sqlBuilder = new SQLBuilder();
        Map<String, String> columnValues = Map.of(
            USERNAME, newUser.getUsername(),
            EMAIL, newUser.getEmail(),
            ID, !newUser.hasId() ? NO_ID : newUser.getId().toString() // TODO: should check that the id is unique if we getId() here
        );
        sqlBuilder.insert(this.getTable(), columnValues);
        LOG(sqlBuilder.toString());
        super.onInsert(newUser, sqlBuilder.toString());
        // create user in user meta table
        // if(super.add(newUser) == null) {
        //     // TODO: maybe catch here
        // }
        
        return newUser;
    }

    private USER simpleAddUser(USER newUser) {
        LOG("ApiDBJdbcUsers::simpleAddUser()");
        if(newUser == null) {
            final String log = LogBuilder.logBuilderFromStringArgs("Given user is null, returning null.").toString();
            LOG(LogLevel.ERROR, log);
            return null;
        }
        
        Map<String, Object> columnValues = Map.of(
            USERNAME, newUser.getUsername() != null ? newUser.getUsername() : "",
            EMAIL, newUser.getEmail() != null ? newUser.getEmail() : "",
            // should check id is in correct format too
            // ID, newUser.hasId() ? newUser.getId() : ApiDB.idManager.generateAndReceiveIdForGivenSet(newUser.name) // TODO: should check that the id is unique if we getId() here
            ID, newUser.getId() != null ? newUser.getId() : ICommon.NO_ID,
            USER_META_DATA, this.serializeUserMetaWithCommonBlob(newUser.getMetaData())
            // USER_META_DATA, (java.sql.Blob) this.serializeUserMetaWithClassSerializer((META) newUser.getMetaData())
        );

        super.onSimpleInsert(newUser, columnValues);
        return newUser;
    }

    private byte[] serializeUserMetaWithCommonBlob(META meta) {
        final String log = LogBuilder.logBuilderFromStringArgs("ApiDBJdbcUsers::serializeUserMetaWithCommonBlob()", meta.toString()).toString();
        LOG(log);
        byte[] byteArray = BlobUtil.serialize(meta);
        // return JdbcBlob.createFromByteArray(byteArray);
        return byteArray;
    }

    // TODO: this needs to be tested more. I don't think it was working correctly last I tried. - chuck 2023/6/29
    // private byte[] serializeUserMetaWithClassSerializer(META meta) {
    //     LogBuilder.logBuilderFromStringArgs("ApiDBJdbcUsers::serializeUserMetaWithClassSerializer()", meta.toString()).info();
    //     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    //     try {
    //         meta.serialize(meta, byteArrayOutputStream);
    //     } catch (IOException e) {
    //         LogBuilder.logBuilderFromStackTraceElementArray(e.getMessage(), e.getStackTrace()).error();
    //     }
    //     byte[] byteArray = byteArrayOutputStream.toByteArray();
    //     // return JdbcBlob.createFromByteArray(byteArray);
    //     return byteArray;
    // }

    @Override
	public USER update(String id, USER newUser) {
        LOG("ApiDBJdbcUsers::update()");
        USER user = super.getById(id); // get user in db
        if(user == null) { // if user not in db return null
            final LogBuilder log = LogBuilder.logBuilderFromStringArgs(
                "Error: Unable to update users with id: ",
                id,
                "  returning null...");
            LOG(log.toString());
            return null;
        }

        // update user details (users table)
        final String username = user.getUsername();
        final String newUsername = newUser.getUsername();
        if(this.manager.userExists(username)) {
            if(username.equals(newUsername)) { // if we don't have to update username (PK) then go through manager
                this.manager.updateUser(newUser.getUserDetails());
            } else { // else pass sql. this will change 'user' table and 'authorities' table PK
                super.onUpdate(UPDATE_USERS_TABLE, new Object[] {newUser.getUsername(), newUser.getPassword(), newUser.getUserDetails().isEnabled(), user.getUsername()});
            }
        }
        
        // update user table
        // username will be changed above if it is different
        super.onUpdate(UPDATE_USER_TABLE, new Object[] {newUser.getEmail(), id}); // TODO this returns number of rows affected, check that it is one. maybe change this method to return int instead.

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

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toString'");
    }
}
