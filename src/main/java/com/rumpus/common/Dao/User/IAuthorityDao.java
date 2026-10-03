package com.rumpus.common.Dao.User;

import java.util.Optional;

import com.rumpus.common.Dao.IDao;
import com.rumpus.common.User.CommonAuthority;

/**
 * DAO for managing authorities.
 */
public interface IAuthorityDao extends IDao<CommonAuthority, Integer> {
    /**
     * Find an authority by its name.
     *
     * @param roleName
     *                 the name of the authority
     * @return an Optional containing the authority if found, or empty if not found
     */
    Optional<CommonAuthority> findByName(String roleName);
}
