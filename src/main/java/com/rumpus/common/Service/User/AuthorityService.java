package com.rumpus.common.Service.User;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rumpus.common.User.CommonAuthority;
import com.rumpus.common.Dao.User.IAuthorityDao;

/**
 * Service implementation for managing {@link CommonAuthority} objects.
 *
 * <p>
 * This class provides the business/service layer between callers and the
 * authority DAO. Database-specific operations should remain inside the DAO.
 * </p>
 */
@Service
public class AuthorityService implements IAuthorityService {

    private final IAuthorityDao authorityDao;

    /**
     * Creates an authority service using the supplied DAO.
     *
     * @param authorityDao
     *                     DAO responsible for authority persistence
     */
    public AuthorityService(IAuthorityDao authorityDao) {
        this.authorityDao = authorityDao;
    }

    /**
     * Gets an authority by ID.
     *
     * @param id
     *           authority ID
     * @return authority if found, otherwise {@code null}
     */
    @Override
    public CommonAuthority getById(Integer id) {
        return authorityDao.getById(id).get();
    }

    /**
     * Gets all authorities.
     *
     * @return list of all authorities
     */
    @Override
    public List<CommonAuthority> getAll() {
        return authorityDao.getAll();
    }

    /**
     * Adds an authority.
     *
     * @param authority
     *                  authority to add
     * @return added authority, or {@code null} if the operation failed
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonAuthority add(CommonAuthority authority) {
        return authorityDao.add(authority);
    }

    /**
     * Creates an authority.
     *
     * <p>
     * Delegates to {@link #add(CommonAuthority)} so that there is a single
     * persistence path for authority creation.
     * </p>
     *
     * @param authority
     *                  authority to create
     * @return created authority, or {@code null} if the operation failed
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonAuthority createAuthority(CommonAuthority authority) {
        return this.add(authority);
    }

    /**
     * Removes an authority by ID.
     *
     * @param id
     *           authority ID
     * @return {@code true} if removed, otherwise {@code false}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Integer id) {
        return authorityDao.remove(id);
    }

    /**
     * Updates an authority by ID.
     *
     * @param id
     *                         ID of the authority to update
     * @param updatedAuthority
     *                         updated authority
     * @return updated authority, or {@code null} if the operation failed
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonAuthority update(
            Integer id,
            CommonAuthority updatedAuthority) {

        return authorityDao.update(id, updatedAuthority);
    }

    /**
     * Gets an authority by its role name.
     *
     * @param name
     *             authority name
     * @return authority if found, otherwise {@code null}
     */
    @Override
    public CommonAuthority getByName(String name) {
        return authorityDao.findByName(name).get();
    }

    /**
     * Determines whether an authority exists with the given role name.
     *
     * @param name
     *             authority name
     * @return {@code true} if the authority exists, otherwise {@code false}
     */
    @Override
    public boolean existsByName(String name) {
        return authorityDao.findByName(name).isPresent();
    }

    /**
     * Removes an authority by its role name.
     *
     * @param name
     *             authority name
     * @return {@code true} if removed, otherwise {@code false}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByName(String name) {
        // return authorityDao.removeByName(name);
        throw new UnsupportedOperationException("Unimplemented method 'removeByName'");
    }

    /**
     * Updates an authority by its role name.
     *
     * @param name
     *                         existing authority name
     * @param updatedAuthority
     *                         updated authority
     * @return updated authority, or {@code null} if the operation failed
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonAuthority updateByName(
            String name,
            CommonAuthority updatedAuthority) {

        // return authorityDao.updateByName(name, updatedAuthority);
        throw new UnsupportedOperationException("Unimplemented method 'updateByName'");
    }
}
