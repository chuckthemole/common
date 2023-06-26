package com.rumpus.common;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.annotation.Id;
import org.springframework.jdbc.support.KeyHolder;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.rumpus.common.Builder.LogBuilder;

public abstract class Model<MODEL extends RumpusObject> extends RumpusObject implements IModel<MODEL> {

    protected static final String NAME = "Model";
    @Id protected String id;
    protected CommonKeyHolder key;
    private TypeAdapter<? extends IModel<MODEL>> typeAdapter;

    // Ctors
    public Model(final String name) {
        super(name);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean hasId() {
        return this.id == null || this.id == NO_ID || this.id == EMPTY_FIELD ? false : true;
    }

    @Override
    public KeyHolder getKey() {
        return this.key;
    }

    @Override
    public void setKey(CommonKeyHolder key) {
        this.key = key;
    }

    @Override
    public TypeAdapter<? extends IModel<MODEL>> getTypeAdapter() {
        return this.typeAdapter;
    }

    @Override
    public void setTypeAdapter(TypeAdapter<? extends IModel<MODEL>> typeAdapter) {
        this.typeAdapter = typeAdapter;
    }

    @Override
    public Map<String, Object> getModelAttributesMap() {
        Map<String, Object> modelAttributesMap = Map.of(ID, this.id, KEYHOLDER, this.key);
        return modelAttributesMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(NAME).append("  id: ").append(id);
        return sb.toString();
    }

    @Override
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
