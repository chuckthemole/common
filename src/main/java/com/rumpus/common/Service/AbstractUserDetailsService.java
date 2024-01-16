package com.rumpus.common.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.rumpus.common.Dao.IDao;
import com.rumpus.common.Model.AbstractModel;

/**
 * TODO: is this being used? if not, delete it
 */
@Service("userDetailsService")
abstract public class AbstractUserDetailsService<MODEL extends AbstractModel<MODEL>> extends com.rumpus.common.Service.AbstractService<MODEL> implements UserDetailsService {
    public AbstractUserDetailsService(String name, IDao<MODEL> dao) {
        super(name, dao);
    }
}
