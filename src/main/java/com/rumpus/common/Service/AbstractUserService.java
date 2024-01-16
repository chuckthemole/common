package com.rumpus.common.Service;

import com.rumpus.common.Dao.IDao;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

abstract public class AbstractUserService
    <
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>
    >
    extends AbstractUserDetailsService<USER> implements IUserService<USER, USER_META> {

        public AbstractUserService(String name, IDao<USER> dao) {
            super(name, dao);
        }
}
