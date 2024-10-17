package com.rumpus.common.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Optional;

import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Serializer.ICommonSerializer;

public interface ISerializerService {

    /**
     * Get the serializer for a given model class.
     * 
     * @param <MODEL> the model class
     * @param clazz the model class
     * @return the an {@link Optional} of the serializer for the model class if present. Otherwise an empty {@link Optional}.
     */
    public <MODEL extends AbstractModel<MODEL, ?>> Optional<ICommonSerializer<MODEL>> getSerializer(Class<MODEL> clazz);

    /**
     * Set the serializer for a given model class.
     * 
     * @param <MODEL> the model class
     * @param clazz the model class
     * @param serializer the serializer for the model class
     */
    public <MODEL extends AbstractModel<MODEL, ?>> void setSerializer(Class<MODEL> clazz, ICommonSerializer<MODEL> serializer);

    /**
     * Serialize the model to the output stream
     * 
     * @param <MODEL> the model class
     * @param model the model to serialize
     * @param stream the output stream to serialize to
     */
    public <MODEL extends AbstractModel<MODEL, ?>> void serialize(MODEL model, OutputStream stream);

    /**
     * Deserialize the model from the input stream
     * 
     * @param <MODEL> the model class
     * @param stream the input stream to deserialize from
     * @param clazz the model class
     * @return the deserialized model
     */
    public <MODEL extends AbstractModel<MODEL, ?>> MODEL deserialize(InputStream stream, Class<MODEL> clazz);

    /**
     * Serialize the model to a string
     * 
     * @param <MODEL> the model class
     * @param model the model to serialize
     * @param charset the charset to use for serialization
     * @return the serialized model as a string
     */
    public <MODEL extends AbstractModel<MODEL, ?>> String serializeToString(MODEL model, Charset charset);
}
