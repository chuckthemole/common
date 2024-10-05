package com.rumpus.common.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

import com.rumpus.common.Manager.AbstractServiceManager;
import com.rumpus.common.Serializer.ISerializerRegistry;

abstract public class AbstractCommonModelConfig 
    <
        SERVICES extends AbstractServiceManager<?>,
        SERIALIZER_REGISTRY extends ISerializerRegistry
    >
    extends AbstractCommonConfig {

        public static final String PARENT_SERVICES = "parentServices";
        public static final String CHILD_SERVICES = "childServices";
        public static final String SERIALIZER_REGISTRY = "serializerRegistry";
        public static final String CHILD_SERIALIZER_REGISTRY = "childSerializerRegistry";

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

        @Bean(name = AbstractCommonModelConfig.SERIALIZER_REGISTRY)
        @DependsOn(AbstractCommonModelConfig.CHILD_SERIALIZER_REGISTRY)
        public SERIALIZER_REGISTRY serializerRegistry() {
            return this.childSerializerRegistry();
        }

        @Bean(name = AbstractCommonModelConfig.CHILD_SERIALIZER_REGISTRY)
        abstract public SERIALIZER_REGISTRY childSerializerRegistry();
    
}
