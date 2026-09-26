package com.rumpus.common.Dao.User;

import java.util.Set;
import java.util.UUID;

import com.rumpus.common.ICommon;
import com.rumpus.common.User.CommonAuthority;

/**
 * Data access contract for managing authorities assigned to users.
 *
 * <p>
 * This interface provides operations for querying, assigning, and removing
 * {@link CommonAuthority} instances associated with a user. User identity is
 * represented by a {@link UUID}, allowing callers to manage authorities without
 * depending on the username-based representation used by the underlying
 * persistence or Spring Security infrastructure.
 * </p>
 *
 * <p>
 * This DAO is concerned specifically with <strong>user-authority
 * assignments</strong>, rather than the creation or management of authority
 * definitions themselves. For example, assigning {@code ROLE_ADMIN} to a user
 * is the responsibility of this DAO, while defining what {@code ROLE_ADMIN}
 * represents belongs to the application's security and authorization model.
 * </p>
 *
 * <p>
 * Implementations should preserve the semantics of the contract: adding an
 * existing role should be idempotent, removing a role that is not assigned
 * should be a no-op, and querying a user's roles should not return duplicate
 * authorities.
 * </p>
 */
public interface IUserAuthorityDao extends ICommon {
    /**
     * Get all roles assigned to a user.
     *
     * <p>
     * If the user exists but has no assigned roles, an empty set should be
     * returned. The returned set should not contain duplicate authorities.
     *
     * @param userId
     *            the unique identifier of the user
     * @return set of authorities assigned to the user, or an empty set if the user
     *         has no assigned authorities
     */
    Set<CommonAuthority> getUserRoles(UUID userId);

    /**
     * Add a role to a user.
     * <p>
     * This method should be idempotent: adding an existing role should not create
     * duplicates or error.
     *
     * @param userId
     *            the user id
     * @param role
     *            the role to assign
     *
     * @throws IllegalArgumentException
     *             if user does not exist or role is invalid
     */
    void addUserRole(UUID userId, CommonAuthority role);

    /**
     * Remove a role from a user.
     * <p>
     * If the user does not have the role, the operation should be a no-op.
     *
     * @param userId
     *            the user id
     * @param role
     *            the role to remove
     *
     * @throws IllegalArgumentException
     *             if user does not exist
     */
    void removeUserRole(UUID userId, CommonAuthority role);
}
