package com.rumpus.common.Dao;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserDao
<
    USER extends com.rumpus.common.User.AbstractCommonUser<USER, META>,
    META extends com.rumpus.common.User.AbstractCommonUserMetaData<META>
> extends IDao<USER> {

    /**
     * TODO: leaving here for now. Look into
     * @param queries
     */
    public void setQueriesFromMap(java.util.Map<String, String> queries);

    /**
     * Get a user by their username.
     * 
     * @param username The username of the user to get.
     * @return The user with the given username. If no user is found, return null. If more than one user is found, return null.
     */
    public USER getByUsername(String username);

    /**
     * Get the user details for a user with the given username.
     * 
     * @param username The username of the user to get the details for.
     * @return The {@link UserDetails} for the user with the given username.
     */
    public UserDetails loadUserByUsername(String username);
}

