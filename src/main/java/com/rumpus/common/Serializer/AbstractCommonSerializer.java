package com.rumpus.common.Serializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.AbstractCommonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;

abstract public class AbstractCommonSerializer<OBJECT extends AbstractCommonObject> extends AbstractCommonObject implements ICommonSerializer<OBJECT> {

    /**
     * Serialization type for this object
     */
    transient private SerializationType serializationType;

    /**
     * Type adapter for this object
     */
    transient private TypeAdapter<OBJECT> typeAdapter; // keep this transient or will not serialize. TODO: moved this from AbstractMetaData, see if should still be transient

    public AbstractCommonSerializer(SerializationType serializationType) {
        this.typeAdapter = this.createTypeAdapter();
        this.serializationType = serializationType;
    }

    /**
     * Abstract class for type adapter creation.
     * 
     * @return type adapter for this MetaData class
     */
    @JsonIgnore protected TypeAdapter<OBJECT> createTypeAdapter() {
        return new TypeAdapter<OBJECT>() {
            @Override
            public void write(JsonWriter out, OBJECT object) throws IOException {
                writeJson(out, object);
            }

            @Override
            public OBJECT read(JsonReader in) throws IOException {
                return readJson(in);
            }
        };
    }

    /**
     * Serialize the object to the output stream in JSON format
     * 
     * @param out the output stream
     * @param object the object to serialize
     * @throws IOException if an error occurs during serialization
     */
    @JsonIgnore abstract protected void writeJson(JsonWriter out, OBJECT object) throws IOException;

    /**
     * Deserialize the object from the input stream in JSON format
     * 
     * @param in the input stream
     * @return the deserialized object
     * @throws IOException if an error occurs during deserialization
     */
    @JsonIgnore abstract protected OBJECT readJson(JsonReader in) throws IOException;

    /******************************************************************************
     *                             PUBLIC SERIALIZE METHODS                       *
     * ----------------------------------------------------------------------------
     *  Purpose: Publicly visible methods to serialize/deserialize                *
     *          objects. Add custom methods here, eg serializeJson                *
     * ----------------------------------------------------------------------------
     *****************************************************************************/
    @Override
    public void serialize(OBJECT object, OutputStream outputStream) throws IOException {
        LOG("AbstractCommonSerializer::serialize()");
        if(this.serializationType == SerializationType.JSON) {
            this.serializeJson(object, outputStream);
        } else {
            throw new UnsupportedOperationException("Unsupported serialization type");
        }
    }

    @Override
    public OBJECT deserialize(InputStream inputStream) throws IOException {
        if(this.serializationType == SerializationType.JSON) {
            return this.deserializeJson(inputStream);
        } else {
            throw new UnsupportedOperationException("Unsupported deserialization type");
        }
    }

    @Override
    public String serializeToString(OBJECT object, Charset charset) {
        if(this.serializationType == SerializationType.JSON) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(outputStream));

            try {
                this.typeAdapter.write(jsonWriter, object);
                jsonWriter.close();
            } catch (IOException e) {
                throw new ProcessingException("Error serializing object to string", e);
            }

            // Convert the byte stream to string with a proper charset, such as UTF-8
            return outputStream.toString(charset != null ? charset : StandardCharsets.UTF_8);
        } else {
            throw new UnsupportedOperationException("Unsupported serialization type");
        }
    }

    @Override
    public OBJECT deserializeFromString(String stringObject, Charset charset) {
        if(this.serializationType == SerializationType.JSON) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(stringObject.getBytes(charset != null ? charset : StandardCharsets.UTF_8));
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, charset != null ? charset : StandardCharsets.UTF_8));

            try {
                return this.typeAdapter.read(jsonReader);
            } catch (IOException e) {
                throw new ProcessingException("Error deserializing object from string", e);
            } finally {
                try {
                    jsonReader.close(); // Ensure reader is closed to prevent resource leak
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new UnsupportedOperationException("Unsupported deserialization type");
        }
    }

    // TODO: Should I have accessors for typeAdapter?
    /**
     * Get the type adapter for this object
     * 
     * @return this type adapter
     */
    @JsonIgnore public TypeAdapter<OBJECT> getTypeAdapter() {
        return this.typeAdapter;
    }

    /**
     * Set the type adapter for this object
     * 
     * @param typeAdapter the type adapter to set
     */
    @JsonIgnore public void setTypeAdapter(TypeAdapter<OBJECT> typeAdapter) {
        this.typeAdapter = typeAdapter;
    }

    @Override
    @JsonIgnore public SerializationType getSerializationType() {
        return this.serializationType;
    }

    @Override
    @JsonIgnore public void setSerializationType(SerializationType serializationType) {
        this.serializationType = serializationType;
    }

    /******************************************************************************
     *                             HELPERS                                        *
     * ----------------------------------------------------------------------------
     *  Purpose: Helper methods for this class.                                   *
     * ----------------------------------------------------------------------------
     *****************************************************************************/

    /**
     * Serialize the object to the output stream in JSON format
     * 
     * @param object the object to serialize
     * @param outputStream the output stream
     * @throws IOException if an error occurs during serialization
     */
    private void serializeJson(OBJECT object, OutputStream outputStream) throws IOException {
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(outputStream));
        this.typeAdapter.write(jsonWriter, object);
        jsonWriter.close();
    }

    /**
     * Deserialize the object from the input stream in JSON format
     * 
     * @param inputStream the input stream
     * @return the deserialized object
     * @throws IOException if an error occurs during deserialization
     */
    private OBJECT deserializeJson(InputStream inputStream) throws IOException {
        return this.typeAdapter
            .read(
                new JsonReader(new InputStreamReader(inputStream))
            );
    }
}
