package com.rumpus.common.Model;

import com.rumpus.common.Serializer.AbstractCommonSerializer;

abstract public class AbstractMetaDataSerializer<META extends AbstractMetaData<META>> extends AbstractCommonSerializer<META> {
    public AbstractMetaDataSerializer(String name, SerializationType serializationType) {
        super(name, serializationType);
    }
}
