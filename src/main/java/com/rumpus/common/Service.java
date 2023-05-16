package com.rumpus.common;

import java.util.List;

public class Service<T extends Model<T>> extends RumpusObject implements IService<T> {

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
}
