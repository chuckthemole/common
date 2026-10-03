package com.rumpus.common.Service.User;

import com.rumpus.common.Service.IService;
import com.rumpus.common.User.CommonAuthority;

/**
 * Service interface for managing {@link CommonAuthority} objects.
 * <p>
 * Extends the generic {@link IService} contract with authority-specific
 * operations based on the authority name.
 * </p>
 *
 * <p>
 * Authority names are expected to be unique. For example: {@code ROLE_USER} and
 * {@code ROLE_ADMIN}.
 * </p>
 */
public interface IAuthorityService extends IService<CommonAuthority, Integer> {

    /**
     * Gets an authority by its role name.
     *
     * @param name
     *             authority name to look for
     * @return authority with the given name, or {@code null} if not found
     */
    public CommonAuthority getByName(String name);

    /**
     * Determines whether an authority with the given role name exists.
     *
     * @param name
     *             authority name to check
     * @return {@code true} if an authority with the given name exists;
     *         {@code false} otherwise
     */
    public boolean existsByName(String name);

    /**
     * Creates an authority.
     * <p>
     * This method is an authority-specific alias for {@link #add(CommonAuthority)}
     * and is useful when the intent of the operation is specifically to create a
     * new role.
     *
     * @param authority
     *                  authority to create
     * @return created authority, or {@code null} if the authority could not be
     *         created
     */
    public CommonAuthority createAuthority(CommonAuthority authority);

    /**
     * Removes an authority by its role name.
     *
     * @param name
     *             authority name to remove
     * @return {@code true} if the authority was removed; {@code false} if it did
     *         not exist or could not be removed
     */
    public boolean removeByName(String name);

    /**
     * Updates an authority identified by its role name.
     *
     * @param name
     *                         name of the authority to update
     * @param updatedAuthority
     *                         authority containing the updated values
     * @return updated authority, or {@code null} if the authority could not be
     *         updated
     */
    public CommonAuthority updateByName(
            String name,
            CommonAuthority updatedAuthority);
}
