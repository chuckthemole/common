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
        return dao.get(id);
    }

    @Override
    public List<T> getAll() {
        return dao.getAll();
    }

    @Override
    public T add(T rumpusModel) {
        return dao.add(rumpusModel);
    }

    @Override
    public boolean remove(int id) {
        return dao.remove(id);
    }
}
