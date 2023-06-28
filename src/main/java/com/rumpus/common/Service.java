package com.rumpus.common;

import java.util.List;

import com.rumpus.common.Dao.IDao;

// import org.springframework.stereotype.Service; TODO: maybe use this annotation here, if so, need to change name of class, since currently conflicts with same name

// @Service
public class Service<T extends AbstractModel<T>> extends RumpusObject implements IService<T> {

    protected static final String NAME = "rawService";

    protected IDao<T> dao;

    public Service() {super(NAME);}
    public Service(String name, IDao<T> dao) {
        super(name);
        this.dao = dao;
    }

    @Override
    public T get(int id) {
        return this.dao.get(id);
    }

    @Override
    public T get(String name) {
        return this.dao.get(name);
    }

    @Override
    public T getById(String id) {
        return this.dao.getById(id);
    }

    @Override
    public List<T> getAll() {
        return this.dao.getAll();
    }

    @Override
    public T add(T rumpusModel) {
        return this.dao.add(rumpusModel);
    }

    @Override
    public boolean remove(int id) {
        return this.dao.remove(id);
    }

    @Override
    public boolean remove(String name) {
        return this.dao.remove(name);
    }
    @Override
    public T update(String id, T updatedModel) {
        return this.dao.update(id, updatedModel);
    }
    // @Override
    // public T update(String id, T updatedModel, String condition) {
    //     return this.dao.update(id, updatedModel, condition);
    // }
}
