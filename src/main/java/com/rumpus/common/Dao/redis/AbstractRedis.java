package com.rumpus.common.Dao.redis;

import java.util.Optional;

import com.rumpus.common.AbstractCommonObject;
import com.rumpus.common.Model.AbstractModel;

public class AbstractRedis<MODEL extends AbstractModel<MODEL>> extends AbstractCommonObject {

    IRedisRepository<MODEL> repository;

    public AbstractRedis(final String name, IRedisRepository<MODEL> repository) {
        super(name);
        this.repository = repository;
    }

    public Optional<MODEL> get(String id) {
        LOG("AbstractRedis::get(id)");
        return this.repository.findById(id);
    }

    public MODEL save(MODEL model) {
        LOG("AbstractRedis::save()");
        return this.repository.save(model);
    }

    public void delete(String id) {
        LOG("AbstractRedis::delete(id)");
        this.repository.deleteById(id);
    }

    public Iterable<MODEL> getAll() {
        LOG("AbstractRedis::getAll()");
        return this.repository.findAll();
    }
}
