package com.rumpus.common.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.rumpus.common.ICommon;
import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Serializer.ICommonSerializer;
import com.rumpus.common.Serializer.ISerializerRegistry;

/**
 * Service Serializer
 * 
 * This class is used to get the serializer for a given model class.
 * It is only accessible in the Service package.
 * 
 * TODO: Maybe rename this? It has an important member variable being autowired. Util seems wrong.
 */
final class SerializerServiceUtil implements ICommon {

    /**
     * The serializer registry
     */
    @Autowired protected static ISerializerRegistry serializerRegistry;
    
    /**
     * Get the serializer for a given model class.
     * Only access in the Service package.
     * 
     * @param <MODEL> the model class
     * @param clazz the model class
     * @return the serializer for the model class
     */
    static <MODEL extends AbstractModel<MODEL, ?>> ICommonSerializer<MODEL> getSerializer(Class<MODEL> clazz) {
        final ICommonSerializer<MODEL> serializer = SerializerServiceUtil.serializerRegistry.getSerializer(clazz);
        return serializer;
    }
}
