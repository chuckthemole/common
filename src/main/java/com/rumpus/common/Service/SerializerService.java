package com.rumpus.common.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Optional;

import com.rumpus.common.AbstractCommonObject;
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
final public class SerializerService extends AbstractCommonObject implements ISerializerService {

    /**
     * The serializer registry
     */
    private ISerializerRegistry serializerRegistry;

    public SerializerService(ISerializerRegistry serializerRegistry) {
        super("SerializerService");
        this.serializerRegistry = serializerRegistry;
    }
    
    @Override
    public <MODEL extends AbstractModel<MODEL, ?>> Optional<ICommonSerializer<MODEL>> getSerializer(Class<MODEL> clazz) {
        final ICommonSerializer<MODEL> serializer = this.serializerRegistry.getSerializer(clazz);
        return Optional.ofNullable(serializer);
    }

    @Override
    public <MODEL extends AbstractModel<MODEL, ?>> void setSerializer(Class<MODEL> clazz, ICommonSerializer<MODEL> serializer) {
        this.serializerRegistry.<MODEL>registerSerializer(clazz, serializer);
    }

    @Override
    public <MODEL extends AbstractModel<MODEL, ?>> void serialize(MODEL model, OutputStream stream) {
        @SuppressWarnings("unchecked")
        final Class<MODEL> clazz = (Class<MODEL>) model.getClass();
        final Optional<ICommonSerializer<MODEL>> serializer = this.getSerializer(clazz);
        if (serializer.isPresent()) {
            try {
                serializer.get().serialize(model, stream);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("No serializer found for class: " + clazz.getName());
        }
    }

    @Override
    public <MODEL extends AbstractModel<MODEL, ?>> MODEL deserialize(InputStream stream, Class<MODEL> clazz) {
        final Optional<ICommonSerializer<MODEL>> serializer = this.getSerializer(clazz);
        if (serializer.isPresent()) {
            try {
                return serializer.get().deserialize(stream);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("No serializer found for class: " + clazz.getName());
        }
        return null;
        
    }

    @Override
    public <MODEL extends AbstractModel<MODEL, ?>> String serializeToString(MODEL model, Charset charset) {
        @SuppressWarnings("unchecked")
        final Class<MODEL> clazz = (Class<MODEL>) model.getClass();
        final Optional<ICommonSerializer<MODEL>> serializer = this.getSerializer(clazz);
        if (serializer.isPresent()) {
            return serializer.get().serializeToString(model, charset);
        } else {
            throw new RuntimeException("No serializer found for class: " + clazz.getName());
        }

    }
}
