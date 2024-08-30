package com.rumpus.common.Dao.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rumpus.common.ICommon;
import com.rumpus.common.Model.AbstractModel;

@Repository
public interface IRedisRepository<MODEL extends AbstractModel<MODEL>> extends CrudRepository<MODEL, String>, ICommon {
}
