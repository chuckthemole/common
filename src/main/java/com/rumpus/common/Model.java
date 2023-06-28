package com.rumpus.common;

import java.io.Serializable;
import java.util.Map;

import org.springframework.core.serializer.Serializer;
import org.springframework.data.annotation.Id;
import org.springframework.jdbc.support.KeyHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.TypeAdapter;
import com.rumpus.common.Builder.LogBuilder;

public abstract class Model<MODEL extends RumpusObject> extends RumpusObject implements Serializable, Serializer<MODEL> {

    protected static final String NAME = "Model";
    @Id protected String id;
    @JsonIgnore transient protected CommonKeyHolder key;
    @JsonIgnore transient private TypeAdapter<MODEL> typeAdapter;

    // Ctors
    public Model() { super(NAME); }
    public Model(final String name) {
        super(name);
    }

    /**
     * Abstract method to implement type adapter creation.
     * 
     * @return type adapter for this MetaData class
     */
    abstract public TypeAdapter<MODEL> createTypeAdapter();

    /**
     * Abstract method for return this model's attributes as a map
     * Should be implemented in each model similar to below:
     * {
     *   Map<String, Object> modelAttributesMap = Map.of(ID, this.id, KEYHOLDER, this.key);
     *   return modelAttributesMap;
     * }
     */
    abstract public Map<String, Object> getModelAttributesMap();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean hasId() {
        return this.id == null || this.id == NO_ID || this.id == EMPTY_FIELD ? false : true;
    }

    public KeyHolder getKey() {
        return this.key;
    }

    public void setKey(CommonKeyHolder key) {
        this.key = key;
    }

    public TypeAdapter<MODEL> getTypeAdapter() {
        return this.typeAdapter;
    }

    public void setTypeAdapter(TypeAdapter<MODEL> typeAdapter) {
        this.typeAdapter = typeAdapter;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(NAME).append("  id: ").append(id);
        return sb.toString();
    }

    public boolean equals(Object o) {
        LOG.info("Model::equals()");
        if (o == this) {
            return true;
        } else if (!(o instanceof Model)) {
            return false;
        }

        @SuppressWarnings(UNCHECKED)
        Model<MODEL> model = (Model<MODEL>) o;

        if(!this.id.equals(model.id)) {
            LogBuilder log = new LogBuilder("\nIds are not equal", "\nModel 1: ", this.getId(), "\nModel 2: ", model.getId());
            log.info();
            return false;
        }
        return true;
    }

    protected boolean idIsEqual(Model<MODEL> model) {
        return this.id.equals(model.id) ? true : false;
    }
}
