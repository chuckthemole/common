package com.rumpus.common.Serializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Manager.IManageable;

/**
 * Interface for a common serializer
 * 
 * @param <OBJECT> the object type to serialize
 */
public interface ICommonSerializer<OBJECT extends AbstractCommonObject> extends Serializer<OBJECT>, Deserializer<OBJECT>, IManageable {

    // create enum for serialization type, ie JSON, XML, etc.
    public enum SerializationType {
        JSON,
        XML,
        CSV,
        InputStream,
    }

    /**
     * Get the serialization type for this object
     * 
     * @return this serialization type
     */
    @JsonIgnore public SerializationType getSerializationType();

    /**
     * Set the serialization type for this object
     * 
     * @param serializationType the serialization type to set
     */
    @JsonIgnore public void setSerializationType(SerializationType serializationType);

    /**
     * Serialize the object to String
     * 
     * @param object the object to serialize
     * @param charset the charset to use for serialization, using {@link StandardCharsets#UTF_8} if null
     * @return the serialized object as a string
     */
    public String serializeToString(OBJECT object, Charset charset);

    /**
     * Deserialize the object from String
     * 
     * @param stringObject the string object to deserialize
     */
    public OBJECT deserializeFromString(String stringObject, Charset charset);
}
