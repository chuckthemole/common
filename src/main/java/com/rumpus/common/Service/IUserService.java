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
        
        /**
         * Get a user by their username.
         * 
         * @param username The username of the user to get.
         * @return The user with the given username. If no user is found, return null. If more than one user is found, return null.
         * TODO: I don't like this. I think it should throw an exception if more than one user is found or maybe an Optional. - chuck
         */
        public USER getByUsername(String username);

        /**
         * Get the key for this service.
         * TODO: look into this more. Am I using this??
         * @return
         */
        public String getKey();
}
