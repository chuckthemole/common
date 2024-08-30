package com.rumpus.common.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

abstract public class AbstractCommonModelConfig 
    <
        SERVICES extends com.rumpus.common.Manager.AbstractServiceManager<?>
    >
    extends AbstractCommonConfig {

        public static final String PARENT_SERVICES = "parentServices";
        public static final String CHILD_SERVICES = "childServices";

        public AbstractCommonModelConfig(String name, Environment environment) {
            super(name, environment);
        }

        @Bean(name = AbstractCommonModelConfig.PARENT_SERVICES)
        @DependsOn(AbstractCommonModelConfig.CHILD_SERVICES)
        public SERVICES userService() {
            return this.childServices();
        }

        @Bean(name = AbstractCommonModelConfig.CHILD_SERVICES)
        abstract public SERVICES childServices();
    
}
