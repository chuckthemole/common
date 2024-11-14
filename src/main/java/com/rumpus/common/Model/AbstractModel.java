package com.rumpus.common.Model;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Serializer.AbstractCommonSerializer;

import jakarta.persistence.MappedSuperclass;

/**
 * Abstract Model
 * 
 * @param <MODEL> the model type
 */
@MappedSuperclass
public abstract class AbstractModel<MODEL extends AbstractCommonObject, ID>
    extends AbstractCommonObject
    implements Serializable, Comparable<MODEL> {

    abstract public static class AbstractModelSerializer<MODEL extends AbstractModel<MODEL, ?>> extends AbstractCommonSerializer<MODEL> {
        public AbstractModelSerializer(SerializationType serializationType) {
            super(serializationType);
        }
    }

    /**
     * The id of the model
     */
    @Id private ID id; // TODO: will have to use org.springframework.data.annotation.Id for NoSQL. Need to figure out how to handle this.

    public AbstractModel() {}

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

    //******************************************************************************
    //*                      java.io.Serializable                                  *
    //* ----------------------------------------------------------------------------
    //*  Purpose: overriding these serializer methods here. right now just using   *
    //*  defaults but can customize.                                               *
    //* ----------------------------------------------------------------------------
    //******************************************************************************
    protected void writeObject(ObjectOutputStream out) throws java.io.IOException {
        out.defaultWriteObject();
    }
    protected void readObject(ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

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

    /**
     * Compare this model to another model
     */
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
}
