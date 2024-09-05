package com.rumpus.common.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.IUserDao;
import com.rumpus.common.Logger.AbstractCommonLogger.LogLevel;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

public class AbstractUserServiceJpa
    <
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>
    > extends AbstractServiceJpa<USER> implements IUserService<USER, USER_META> {

        protected IUserDao<USER, USER_META> userDao; // TODO: should this be private?

        public AbstractUserServiceJpa(String name, IUserDao<USER, USER_META> userDao) {
            super(name, userDao);
            this.userDao = userDao;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            LOG_THIS("loadUserByUsername(username)");
            return this.userDao.loadUserByUsername(username);
        }

        @Override
        public USER getByUsername(String username) {
            LOG_THIS("getByUsername(username)");
            return this.userDao.getByUsername(username);
        }

        @Override
        public String getKey() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getKey'");
        }

        private static void LOG_THIS(String... args) {
            ICommon.LOG(AbstractUserServiceJpa.class, args);
        }

        private static void LOG_THIS(LogLevel level, String... args) {
            ICommon.LOG(AbstractUserServiceJpa.class, level, args);
        }
    
}
