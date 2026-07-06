package com.rumpus.common.Dao.User;

import java.util.Optional;

import com.rumpus.common.Dao.IDao;
import com.rumpus.common.User.AbstractCommonUser;
import com.rumpus.common.User.AbstractCommonUserMetaData;

/**
 * DAO for managing users.
 */
public interface IUserDao<USER extends AbstractCommonUser<USER, META>, META extends AbstractCommonUserMetaData<META>>
        extends IDao<USER> {

    /**
     * Get a user by their username.
     *
     * @param username
     *                 The username of the user to get.
     * @return The user with the given username. If no user is found, return null.
     *         If more than one user is found, return null.
     */
    public Optional<USER> getByUsername(String username);

    /**
     * Check if a user exists with the given username.
     * 
     * @param username The username of the user to check.
     * @return True if a user with the given username exists, false otherwise.
     */
    boolean existsByUsername(String username);
}
