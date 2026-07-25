package com.rumpus.common.Config.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.User.IUserDao;
import com.rumpus.common.Service.User.IUserService;
import com.rumpus.common.Service.User.UserSecurityService;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;
import com.rumpus.common.User.AbstractUserFactory;

abstract public class AbstractCommonUserConfig<USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends IUserService<USER, USER_META>,
        USER_DAO extends IUserDao<USER, USER_META>,
        USER_FACTORY extends AbstractUserFactory<USER, USER_META>>
        extends
            AbstractCommonObject {

    public static final String USER_SERVICE = "parentUserService";
    public static final String CHILD_USER_SERVICE = "createUserService";

    public AbstractCommonUserConfig() {
    }

    @Bean(name = AbstractCommonUserConfig.USER_SERVICE)
    @Primary
    public USER_SERVICE userService(
            USER_DAO userDao,
            UserSecurityService userSecurityService,
            USER_FACTORY userFactory,
            PasswordEncoder passwordEncoder) {
        return this.createUserService(
                userDao,
                userSecurityService,
                userFactory,
                passwordEncoder);
    }

    abstract public USER_SERVICE createUserService(
            USER_DAO userDao,
            UserSecurityService userSecurityService,
            USER_FACTORY userFactory,
            PasswordEncoder passwordEncoder);
}
