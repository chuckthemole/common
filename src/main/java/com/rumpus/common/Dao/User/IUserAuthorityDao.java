package com.rumpus.common.Dao.User;

import java.util.List;
import java.util.UUID;

/**
 * DAO for managing user authorities.
 */
public interface IUserAuthorityDao { // TODO: make authority model so this can extend IDao<AUTH>
    /**
     * Get all roles assigned to a user.
     *
     * @param userId the user id
     * @return list of role names assigned to the user
     */
    List<String> getUserRoles(UUID userId);

    /**
     * Add a role to a user.
     * <p>
     * This method should be idempotent:
     * adding an existing role should not create duplicates or error.
     *
     * @param userId the user id
     * @param role   the role to assign
     *
     * @throws IllegalArgumentException if user does not exist or role is invalid
     */
    void addUserRole(UUID userId, String role);

    /**
     * Remove a role from a user.
     * <p>
     * If the user does not have the role, the operation should be a no-op.
     *
     * @param userId the user id
     * @param role   the role to remove
     *
     * @throws IllegalArgumentException if user does not exist
     */
    void removeUserRole(UUID userId, String role);
}
