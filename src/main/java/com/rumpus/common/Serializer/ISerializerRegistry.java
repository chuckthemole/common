package com.rumpus.common.Serializer;

import com.rumpus.common.AbstractCommonObject;

/**
 * Interface for a serializer registry
 * 
 * @param <OBJECT> the object type to serialize
 */
public interface ISerializerRegistry {
    
    /**
     * Register a serializer for a class
     * 
     * @param clazz The class to register the serializer for
     * @param serializer The serializer to register
     * @param <OBJECT> The object type to serialize
     */
    public <OBJECT extends AbstractCommonObject> void registerSerializer(Class<OBJECT> clazz, ICommonSerializer<OBJECT> serializer);

    /**
     * Get a serializer for a class
     * 
     * @param clazz The class to get the serializer for
     * @param <OBJECT> The object type to serialize
     * @return The serializer for the class
     */
    public <OBJECT extends AbstractCommonObject> ICommonSerializer<OBJECT> getSerializer(Class<OBJECT> clazz);
}
