package com.rumpus.common.Dao.User;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.rumpus.common.ICommon;
import com.rumpus.common.User.CommonAuthority;
import com.rumpus.common.User.UserAuthority;

/**
 * 
 * Data access contract for managing authorities assigned to users.
 *
 * <p>
 * This interface provides operations for querying, assigning, and removing
 * {@link CommonAuthority} instances associated with a user. User identity is
 * represented by a {@link String}, allowing callers to manage authorities using
 * the username representation used by the underlying persistence or Spring
 * Security infrastructure.
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
 * 
 */
public interface IUserAuthorityDao extends ICommon {

    /**
     * Get all roles assigned to a user.
     *
     * <p>
     * If the user exists but has no assigned roles, an empty set should be
     * returned. The returned set should not contain duplicate authorities.
     *
     * @param username
     *                 the unique username of the user
     * @return set of {@link UserAuthority} assigned to the user, or an empty set if
     *         the user
     *         has no assigned authorities
     */
    Set<UserAuthority> getUserRoles(String username);

    /**
     * Add a role to a user.
     *
     * <p>
     * This method is idempotent. If the user already has the specified role,
     * the existing {@link UserAuthority} is returned rather than creating a
     * duplicate assignment.
     *
     * @param username
     *                  the username of the user receiving the role
     *
     * @param role
     *                  the role to assign
     *
     * @param grantedBy
     *                  the UUID of the user granting the role
     *
     * @param expiresAt
     *                  the optional expiration timestamp for the role;
     *                  {@code null} indicates that the role does not expire
     *
     * @return the newly created or already-existing {@link UserAuthority}
     *         assignment
     *
     * @throws IllegalArgumentException
     *                                  if the user does not exist or the role is
     *                                  invalid
     */
    UserAuthority addUserRole(
            String username,
            CommonAuthority role,
            UUID grantedBy,
            Instant expiresAt);

    /**
     * Remove a role from a user.
     *
     * <p>
     * If the user exists but does not have the specified role, this method is
     * a no-op.
     *
     * @param username
     *                 the username of the user
     * @param role
     *                 the role to remove
     *
     * @throws IllegalArgumentException
     *                                  if the user does not exist
     */
    void removeUserRole(String username, CommonAuthority role);

    /**
     * Get a specific role assigned to a user.
     * <p>
     * If the user does not have the role, {@code null} should be returned.
     * 
     * @param username the username of the user
     * @param role     the role to get
     * @return the user authority if found, otherwise null
     */
    UserAuthority getUserRole(String username, CommonAuthority role);

    /**
     * Get a user-authority assignment by its database ID.
     *
     * @param userAuthorityId
     *                        database ID of the user-authority assignment
     *
     * @return the matching user-authority assignment, or {@code null} if no
     *         assignment exists
     */
    UserAuthority getUserRoleById(Integer userAuthorityId);

    /**
     * Update the expiration timestamp of an existing user-authority assignment.
     *
     * <p>
     * This method updates the {@code expires_at} value associated with the
     * specified {@link UserAuthority}. The authority itself, the assigned user,
     * the original creation timestamp, and the user who granted the authority
     * are not changed.
     * </p>
     *
     * <p>
     * A {@code null} expiration timestamp indicates that the authority should
     * not expire.
     * </p>
     *
     * @param userAuthorityId
     *                        the database ID of the {@link UserAuthority}
     *                        assignment to update
     *
     * @param expiresAt
     *                        the new expiration timestamp for the assignment;
     *                        {@code null} indicates that the authority does not
     *                        expire
     *
     * @return the updated {@link UserAuthority} assignment
     *
     * @throws IllegalArgumentException
     *                                  if no user-authority assignment exists
     *                                  with the specified ID
     */
    UserAuthority updateUserRole(
            Integer userAuthorityId,
            Instant expiresAt);

}
