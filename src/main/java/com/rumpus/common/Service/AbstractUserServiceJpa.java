package com.rumpus.common.Service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rumpus.common.ICommon;
import com.rumpus.common.Dao.IUserDaoJpa;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

abstract public class AbstractUserServiceJpa
    <
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>
    > extends AbstractServiceJpa<USER> implements IUserService<USER, USER_META> {

        private IUserDaoJpa<USER, USER_META> userDaoJpa;

        public AbstractUserServiceJpa(IUserDaoJpa<USER, USER_META> userDaoJpa) {
            super(userDaoJpa);
            this.userDaoJpa = userDaoJpa;
        }

        /**
         * Create a user with the given username.
         * 
         * @param username The username of the user to create.
         * @return A user with the given username.
         */
        abstract public USER createUserWithUsername(String username);

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            LOG_THIS("loadUserByUsername(username)");
            return this.getByUsername(username).getUserDetails(); // TODO: catch NoSuchElementException
        }

        @Override
        public USER getByUsername(String username) {
            USER exampleUser = this.createUserWithUsername(username);
            ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues() // ignore null values
                .withMatcher(ICommon.USERNAME, ExampleMatcher.GenericPropertyMatchers.exact());
            Example<USER> example = Example.of(exampleUser, matcher);
            return this.userDaoJpa.findOne(example).get(); // TODO: catch NoSuchElementException
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
