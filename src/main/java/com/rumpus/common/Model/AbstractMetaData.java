package com.rumpus.common.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Serializer.AbstractCommonSerializer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Abstract class for Model meta data. This holds some of the common member variables, like creationTime, and interface that each Model shares.
 * 
 * Child class can override the writeObject and readObject methods of Serializeable.
 * Child class should override Serializer<META> serialize() method.
 * <p>
 * TODO: This class is implementing Serializable. According to Effective Java, this may not be a good idea. I should look into this more.
 */
public abstract class AbstractMetaData<
    META extends AbstractMetaData<META>
> extends AbstractMeta implements Serializable {

    private static final long serialVersionUID = META_DATA_UID;

    transient public static final String USER_CREATION_DATE_TIME = "user_creation_datetime";
    transient protected static final String NAME_KEY = "name";
    transient protected static final String CREATION_TIME_KEY = "creationTime";

    protected String creationTime; // using epoch 1970

    /**
     * Serializer for this object
     */
    private transient AbstractCommonSerializer<META> serializer;

    public AbstractMetaData() {
        this.creationTime = String.valueOf(Instant.now().toEpochMilli());
    }

    public AbstractMetaData(String creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Be sure to have NAME_KEY and CREATION_TIME_KEY as keys in your map if you use this
     * 
     * @param attributesMap
     */
    public AbstractMetaData(java.util.Map<String, Object> attributesMap) {
        this.creationTime = (String) attributesMap.get(CREATION_TIME_KEY);
    }

    /**
     * Abstract method for return this MetaData's attributes as a map
     * Should be implemented in each model similar to below:
     * {
     *   Map<String, Object> metaAttributesMap = Map.of(ID, this.id, KEYHOLDER, this.key);
     *   return metaAttributesMap;
     * }
     */
    @JsonIgnore abstract public java.util.Map<String, Object> getMetaAttributesMap();

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
        Long creationTimeLong = null;
        try {
            creationTimeLong = Long.valueOf(this.creationTime);
        } catch (NumberFormatException e) {
            LOG("Creation time is not a number: ", this.creationTime);
            return "Invalid creation time";
        }
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(creationTimeLong),
            ZoneOffset.UTC).toString();
        // return LocalDateTime.ofInstant(this.creationTime, ZoneOffset.UTC).toString();
    }

    // overriding these serializer methods here. right now just using defaults but can customize
    protected void writeObject(ObjectOutputStream out) throws java.io.IOException {
        LOG("AbstractMetaData::writeObject()");
        out.defaultWriteObject();
    }
    protected void readObject(ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        LOG("AbstractMetaData::readObject()");
        in.defaultReadObject();
    }

    // TODO: should I be overriding equals and hashcode here? 2023/6/28
    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractMetaData)) {
            return false;
        }

        AbstractMetaData<META> meta = (AbstractMetaData<META>) o;

        boolean isEqual = true;
        java.util.Map<String, Object> thisAttributeMap = this.getMetaAttributesMap();
        java.util.Map<String, Object> otherAttributeMap = meta.getMetaAttributesMap();
        if(thisAttributeMap.size() != otherAttributeMap.size()) {
            final String log = LogBuilder.logBuilderFromStringArgs(
                "Meta data not equal: different sizes ( ",
                String.valueOf(thisAttributeMap.size()),
                " : ",
                String.valueOf(otherAttributeMap.size()),
                " )").toString();
            LOG(log);
            isEqual = false;
        } else {
            for(java.util.Map.Entry<String, Object> entrySet : thisAttributeMap.entrySet())  {
                final String key = entrySet.getKey();
                final Object value = entrySet.getValue();
                if(!otherAttributeMap.containsKey(key)) {
                    final String log = LogBuilder.logBuilderFromStringArgs(
                        "Meta data not equal: doesn't contain key ( ",
                        key,
                        " )").toString();
                    LOG(log);
                    isEqual = false;
                } else {
                    if(!value.equals(otherAttributeMap.get(key))) {
                        final String log = LogBuilder.logBuilderFromStringArgs(
                            "Meta data not equal: values not equal ( ",
                            value.toString(),
                            " : ",
                            otherAttributeMap.get(key).toString(),
                            " )").toString();
                        LOG(log);
                        isEqual = false;
                    }
                }
            }
        }
        return isEqual;
    }
}
