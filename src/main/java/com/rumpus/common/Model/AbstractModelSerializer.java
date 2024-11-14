package com.rumpus.common.Model;

import com.rumpus.common.Serializer.AbstractCommonSerializer;

abstract public class AbstractModelSerializer<MODEL extends AbstractModel<MODEL, ?>> extends AbstractCommonSerializer<MODEL> {
    public AbstractModelSerializer(String name, SerializationType serializationType) {
        super(serializationType);
    }
}
