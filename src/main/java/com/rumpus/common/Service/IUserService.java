package com.rumpus.common.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

@Service("userDetailsService") // TODO: look at this annotation and see if we could put in IService? or remove it? - chuck
public interface IUserService
    <
        USER extends AbstractCommonUser<USER, META>,
        META extends AbstractCommonUserMetaData<META>
    > extends IService<USER>, UserDetailsService {
        
        public String getKey();
}
