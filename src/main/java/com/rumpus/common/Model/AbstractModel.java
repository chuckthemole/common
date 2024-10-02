package com.rumpus.common.Model;

import java.io.Serializable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.springframework.core.serializer.Serializer;

import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.rumpus.common.AbstractCommonObject;

import jakarta.persistence.MappedSuperclass;

/**
 * Abstract Model
 * 
 * @param <MODEL> the model type
 */
@MappedSuperclass
public abstract class AbstractModel<MODEL extends AbstractCommonObject, ID> extends AbstractCommonObject
    implements Serializable, Serializer<MODEL>, Comparable<MODEL> {

    /**
     * The id of the model
     */
    @Id private ID id; // TODO: will have to use org.springframework.data.annotation.Id for NoSQL. Need to figure out how to handle this.
    
    /**
     * The type adapter for this model
     * 
     * TODO: take this out of here and use the serializer {@link com.rumpus.common.Serializer.AbstractCommonSerializer}
     */
    @JsonIgnore transient private TypeAdapter<MODEL> typeAdapter;

    public AbstractModel(final String name) {
        super(name);
        this.typeAdapter = this.createTypeAdapter();
    }

    /******************************************************************************
     *                       ID management / JSON                                 *
     * ----------------------------------------------------------------------------
     *  Purpose:                                                                  *
     * ----------------------------------------------------------------------------
     *****************************************************************************/

    /**
     * Generate a new ID for this model
     */
    public void generateId() {
        this.id = getIdManager().generateId();
    }

    /**
     * Validate the ID of this model
     * 
     * @return true if the ID is valid, false otherwise
     */
    public boolean validateId() {
        return getIdManager().validateId(this.id);
    }

    /**
     * Abstract method to implement the ID manager.
     * 
     * TODO: the ID manager should be a singleton, so this should be a static method, or a static member variable.
     * for now, child class should have a static member variable, idManager, that implements and is used to return here.
     * 
     * @return the ID manager for this model
     */
    @JsonIgnore
    public abstract IModelIdManager<ID> getIdManager();

    /**
     * Abstract method to implement type adapter creation.
     * 
     * @return type adapter for this MetaData class
     */
    abstract public TypeAdapter<MODEL> createTypeAdapter();

    //******************************************************************************
    //*                      access/getters/setter                                 *
    //* ----------------------------------------------------------------------------
    //*  Purpose: Isn't it obvious                                                 *
    //* ----------------------------------------------------------------------------
    //******************************************************************************

    public ID getId() {
        return this.id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public boolean hasId() {
        return this.id != null;
    }

    public TypeAdapter<MODEL> getTypeAdapter() {
        return this.typeAdapter;
    }

    public void setTypeAdapter(TypeAdapter<MODEL> typeAdapter) {
        this.typeAdapter = typeAdapter;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
    
        // Ensure the object is an instance of AbstractModel
        if (o instanceof AbstractModel<?, ?>) {
            AbstractModel<?, ?> model = (AbstractModel<?, ?>) o;
    
            // Compare the ids safely
            if (this.id != null && this.id.equals(model.id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void serialize(MODEL object, OutputStream outputStream) throws IOException {
        this.getTypeAdapter().write(new JsonWriter(new OutputStreamWriter(outputStream)), object);
    }
}
