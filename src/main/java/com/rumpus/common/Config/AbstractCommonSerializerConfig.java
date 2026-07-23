package com.rumpus.common.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Serializer.ISerializerRegistry;

abstract public class AbstractCommonSerializerConfig extends AbstractCommonObject {

    public static final String PARENT_SERIALIZER_REGISTRY = "parentSerializerRegistry";
    public static final String CHILD_SERIALIZER_REGISTRY = "childSerializerRegistry";

    public AbstractCommonSerializerConfig() {
    }

    @Bean(name = AbstractCommonSerializerConfig.PARENT_SERIALIZER_REGISTRY)
    @DependsOn(AbstractCommonSerializerConfig.CHILD_SERIALIZER_REGISTRY)
    public ISerializerRegistry parentSerializerRegistry() {
        return this.childSerializerRegistry();
    }

    @Bean(name = AbstractCommonSerializerConfig.CHILD_SERIALIZER_REGISTRY)
    abstract public ISerializerRegistry childSerializerRegistry();

}
