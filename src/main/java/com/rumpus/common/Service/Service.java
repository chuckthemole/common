package com.rumpus.common.Service;

import java.util.List;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Model.AbstractModel;

// import org.springframework.stereotype.Service; TODO: maybe use this annotation here, if so, need to change name of class, since currently conflicts with same name

// @Service
abstract public class Service<T extends AbstractModel<T>> extends AbstractCommonObject {

    protected static final String NAME = "rawService";

    protected IDao<T> dao;

    public Service() {super(NAME);}
    public Service(String name, IDao<T> dao) {
        super(name);
        this.dao = dao;
    }

    public T get(int id) {
        return this.dao.get(id);
    }

    public T get(String name) {
        return this.dao.get(name);
    }

    public T getById(String id) {
        return this.dao.getById(id);
    }

    public List<T> getAll() {
        return this.dao.getAll();
    }

    public T add(T rumpusModel) {
        return this.dao.add(rumpusModel);
    }

    public boolean remove(int id) {
        return this.dao.remove(id);
    }

    public boolean remove(String name) {
        return this.dao.remove(name);
    }

    public T update(String id, T updatedModel) {
        return this.dao.update(id, updatedModel);
    }
    // @Override
    // public T update(String id, T updatedModel, String condition) {
    //     return this.dao.update(id, updatedModel, condition);
    // }
}
