package com.rumpus.common;

import java.util.Map;

import org.springframework.jdbc.support.KeyHolder;

import com.google.gson.TypeAdapter;

public interface IModel<MODEL extends IRumpusObject> extends IRumpusObject {
    /**
     * 
     * @return this model's id
     */
    String getId();
    /**
     * 
     * @param id set this model's id
     */
    void setId(String id);
    /**
     * 
     * @return true if this model has an id, otherwise false. will check for null, NO_ID, and EMPTY_FIELD
     */
    boolean hasId();
    /**
     * 
     * @return the KeyHolder object for this model
     */
    KeyHolder getKey();
    /**
     * 
     * @param key key to set as KeyHolder object for this model
     */
    void setKey(CommonKeyHolder key);
    /**
     * 
     * @return the attributes (usually member variables) for this MODEL as a map
     */
    Map<String, Object> getModelAttributesMap();
    /**
     * 
     * @return
     */
    TypeAdapter<? extends IModel<MODEL>> getTypeAdapter();
    /**
     * 
     * @param typeAdapter
     */
    void setTypeAdapter(TypeAdapter<? extends IModel<MODEL>> typeAdapter);
}
