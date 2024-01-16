package com.rumpus.common.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.rumpus.common.Service.IUserService;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

abstract public class AbstractCommonUserConfig
    <
        USER extends AbstractCommonUser<USER, USER_META>,
        USER_META extends AbstractCommonUserMetaData<USER_META>,
        USER_SERVICE extends IUserService<USER, USER_META>
    >
    extends AbstractCommonConfig {

        public static final String USER_SERVICE = "parentUserService";
        public static final String CHILD_USER_SERVICE = "childUserService";

        @Bean(name = AbstractCommonUserConfig.USER_SERVICE)
        @DependsOn(AbstractCommonUserConfig.CHILD_USER_SERVICE)
        public USER_SERVICE userService() {
            return this.childUserService();
        }

        @Bean(name = AbstractCommonUserConfig.CHILD_USER_SERVICE)
        abstract public USER_SERVICE childUserService();
}
