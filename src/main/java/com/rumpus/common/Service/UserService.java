package com.rumpus.common.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Model.AbstractModel;

@Service("userDetailsService")
abstract public class UserService<MODEL extends AbstractModel<MODEL>> extends com.rumpus.common.Service.Service<MODEL> implements UserDetailsService {
    public UserService(String name, IDao<MODEL> dao) {
        super(name, dao);
    }
}
