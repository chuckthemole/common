package com.rumpus.common.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

import com.rumpus.common.Manager.AbstractServiceManager;
import com.rumpus.common.Serializer.ISerializerRegistry;
import com.rumpus.common.Service.ISerializerService;
import com.rumpus.common.Service.SerializerService;

abstract public class AbstractCommonModelConfig 
    <
        SERVICES extends AbstractServiceManager<?>,
        SERIALIZER_REGISTRY_SERVICE extends ISerializerRegistry
    >
    extends AbstractCommonConfig {

        public static final String PARENT_SERVICES = "parentServices";
        public static final String CHILD_SERVICES = "childServices";
        public static final String SERIALIZER_REGISTRY = "serializerRegistry";
        public static final String CHILD_SERIALIZER_REGISTRY = "childSerializerRegistry";
        public static final String PARENT_SERIALIZER_SERVICE = "parentSerializerService";
        public static final String CHILD_SERIALIZER_SERVICE = "childSerializerService";

        public AbstractCommonModelConfig(Environment environment) {
            super(environment);
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
        public SERIALIZER_REGISTRY_SERVICE serializerRegistry() {
            return this.childSerializerRegistry();
        }

        @Bean(name = AbstractCommonModelConfig.CHILD_SERIALIZER_REGISTRY)
        abstract public SERIALIZER_REGISTRY_SERVICE childSerializerRegistry();
    
        @Bean(name = AbstractCommonModelConfig.PARENT_SERIALIZER_SERVICE)
        @DependsOn(AbstractCommonModelConfig.CHILD_SERIALIZER_SERVICE)
        public ISerializerService serializerService() {
            final ISerializerService serializerService = new SerializerService(this.childSerializerService());
            return serializerService;
        }

        /**
         * Implement this method to return {@link ISerializerRegistry} for the parent to consume.
         * 
         * @return {@link ISerializerRegistry} for the parent to consume
         */
        @Bean(name = AbstractCommonModelConfig.CHILD_SERIALIZER_SERVICE)
        abstract public ISerializerRegistry childSerializerService();
}
