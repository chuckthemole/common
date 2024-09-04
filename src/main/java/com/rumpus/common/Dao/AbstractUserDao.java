package com.rumpus.common.Dao;

import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.ICommon;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.rumpus.common.Dao.jdbc.Mapper;

abstract public class AbstractUserDao
<
    USER extends com.rumpus.common.User.AbstractCommonUser<USER, USER_META>,
    USER_META extends com.rumpus.common.User.AbstractCommonUserMetaData<USER_META>
> extends AbstractDao<USER> implements IUserDao<USER, USER_META> {

    public AbstractUserDao(String name, String table, String metaTable, Mapper<USER> mapper) {
        super(name, table, metaTable, mapper);
    }

    @Override
    public USER getByUsername(String username) {
        LOG_THIS("AbstractUserDao::getByUsername(username)");
        final List <USER> users = this.getByColumnValue(ICommon.USERNAME, username);
        if (users.size() == 1) {
            USER user = users.get(0);
            UserDetails details = this.loadUserByUsername(username);
            user.setUserDetails(details);
            return user;
        } else if (users.size() == 0) {
            LOG_THIS("No user found with username: " + username);
            return null;
        } else {
            LOG_THIS("More than one user found with username: " + username);
            return null;
        }
    }

    private static void LOG_THIS(String... args) {
        ICommon.LOG(AbstractUserDao.class, args);
    }

    private static void LOG_THIS(LogLevel level, String... args) {
        ICommon.LOG(AbstractUserDao.class, level, args);
    }
    
}
