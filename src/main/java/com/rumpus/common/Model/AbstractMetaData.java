package com.rumpus.common.Model;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import org.springframework.core.serializer.Serializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.TypeAdapter;
import com.rumpus.common.AbstractCommonObject;

/**
 * Abstract class for Model meta data. This holds some of the common member variables, like creationTime, and interface that each Model shares.
 * 
 * Child class can override the writeObject and readObject methods of Serializeable.
 * Child class should override Serializer<META> serialize() method.
 * 
 */
public abstract class AbstractMetaData<META extends AbstractMetaData<META>> extends AbstractCommonObject implements Serializable, Serializer<META> {

    private static final long serialVersionUID = META_DATA_UID;

    private static final String NAME = "MetaData";
    public static final String USER_CREATION_DATE_TIME = "user_creation_datetime";

    protected String creationTime; // using epoch 1970
    transient private TypeAdapter<META> typeAdapter; // keep this transient or will not serialize

    public AbstractMetaData() {
        super(NAME);
        this.creationTime = String.valueOf(Instant.now().toEpochMilli());
    }
    public AbstractMetaData(String name) {
        super(name);
        this.creationTime = String.valueOf(Instant.now().toEpochMilli());
    }
    public AbstractMetaData(String name, String creationTime) {
        super(name);
        this.creationTime = creationTime;
    }

    /**
     * Abstract class to implement type adapter creation.
     * 
     * @return type adapter for this MetaData class
     */
    @JsonIgnore abstract public TypeAdapter<META> createTypeAdapter();

    /**
     * Abstract method for return this MetaData's attributes as a map
     * Should be implemented in each model similar to below:
     * {
     *   Map<String, Object> metaAttributesMap = Map.of(ID, this.id, KEYHOLDER, this.key);
     *   return metaAttributesMap;
     * }
     */
    @JsonIgnore abstract public Map<String, Object> getMetaAttributesMap();

    /**
     * 
     * @return this type adapter
     */
    @JsonIgnore public TypeAdapter<META> getTypeAdapter() {
        return this.typeAdapter;
    }

    /**
     * 
     * @param typeAdapter
     */
    @JsonIgnore public void setTypeAdapter(TypeAdapter<META> typeAdapter) {
        this.typeAdapter = typeAdapter;
    }

    /**
     * 
     * @return the creation time as Instant
     */
    public String getCreationTime() {
        return this.creationTime;
    }

    /**
     * 
     * @param creationTime
     */
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 
     * @return LocalDateTime object to string using ZoneOffset.UTC <- TODO look into this more. may have to come up with standards for time.
     */
    public final String getStandardFormattedCreationTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(this.creationTime)), ZoneOffset.UTC).toString();
        // return LocalDateTime.ofInstant(this.creationTime, ZoneOffset.UTC).toString();
    }

    // overriding these serializer methods here. right now just using defaults but can customize
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        LOG.info("AbstractMetaData::writeObject()");
        out.defaultWriteObject();
    }
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        LOG.info("AbstractMetaData::readObject()");
        in.defaultReadObject();
    }
}
