package com.rumpus.common.Serializer;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Manager.IManageable;

/**
 * Interface for a common serializer
 * 
 * @param <OBJECT> the object type to serialize
 */
public interface ICommonSerializer<OBJECT extends AbstractCommonObject> extends Serializer<OBJECT>, Deserializer<OBJECT>, IManageable {

    /**
     * Serialize an object to a JSON string
     * 
     * @param object The object to serialize
     * @return The JSON string
     */
    public String serializeToJson(OBJECT object);
}
