package com.rumpus.common.User;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.IModelIdManager;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 * Represents the assignment of an authority to a user.
 *
 * <p>
 * A {@code UserAuthority} is distinct from {@link CommonAuthority}. A
 * {@code CommonAuthority} represents an authority definition such as
 * {@code ROLE_USER} or {@code ROLE_ADMIN}, while this class represents the
 * relationship between a user and an authority.
 * </p>
 *
 * <p>
 * The assignment may also contain metadata describing when and by whom the
 * authority was granted and, optionally, when the assignment expires.
 * </p>
 *
 * <p>
 * This class corresponds to the {@code user_authorities} persistence table.
 * The database identity of an assignment is represented by an {@link Integer}.
 * </p>
 * 
 */
@Entity
@Table(name = "user_authorities")
@Schema(description = "An authority assigned to a user.")
public class UserAuthority extends AbstractModel<UserAuthority, Integer> {

    /**
     * Username of the user receiving the authority.
     *
     * <p>
     * This is intentionally represented as the username rather than a UUID.
     * Username is the current user identity used by the authorization DAO and
     * persistence layer.
     * </p>
     */
    @Column(name = "username", nullable = false, length = 50)
    @Schema(description = "Username of the user receiving the authority.")
    private String username;

    /**
     * Authority assigned to the user.
     *
     * <p>
     * This represents the authority definition itself, such as
     * {@code ROLE_USER} or {@code ROLE_ADMIN}.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "authority_id", nullable = false)
    @Schema(description = "Authority assigned to the user.")
    private CommonAuthority authority;

    /**
     * Timestamp at which the authority was assigned.
     *
     * <p>
     * The database should normally provide the default value for this column.
     * </p>
     */
    @Column(name = "created_at", nullable = false)
    @Schema(description = "Timestamp at which the authority was assigned.")
    private Instant createdAt;

    /**
     * UUID of the user who granted the authority.
     *
     * <p>
     * This may be {@code null} when the assignment is created automatically by
     * the system rather than explicitly granted by another user.
     * </p>
     */
    @Column(name = "granted_by", length = 50)
    @Schema(description = "UUID of the user who granted the authority.")
    private UUID grantedBy;

    /**
     * Timestamp at which the authority assignment expires.
     *
     * <p>
     * A {@code null} value indicates that the assignment does not have an
     * expiration time.
     * </p>
     */
    @Column(name = "expires_at")
    @Schema(description = "Timestamp at which the authority assignment expires.")
    private Instant expiresAt;

    /**
     * Required no-argument constructor for JPA.
     *
     * <p>
     * This constructor should not normally be called directly by application
     * code.
     * </p>
     */
    protected UserAuthority() {
        super();
    }

    /**
     * Create a user-authority assignment.
     *
     * @param username
     *                  username receiving the authority
     * @param authority
     *                  authority being assigned
     */
    public UserAuthority(
            final String username,
            final CommonAuthority authority) {

        super();

        this.username = Objects.requireNonNull(
                username,
                "username cannot be null");

        this.authority = Objects.requireNonNull(
                authority,
                "authority cannot be null");

        this.createdAt = Instant.now();
    }

    /**
     * Create a user-authority assignment with expiration and grant metadata.
     *
     * @param username
     *                  username receiving the authority
     * @param authority
     *                  authority being assigned
     * @param grantedBy
     *                  UUID of the user granting the authority
     * @param expiresAt
     *                  optional expiration timestamp
     */
    public UserAuthority(
            final String username,
            final CommonAuthority authority,
            final UUID grantedBy,
            final Instant expiresAt) {

        this(username, authority);

        this.grantedBy = grantedBy;
        this.expiresAt = expiresAt;
    }

    /**
     * Reconstruct a persisted user-authority assignment.
     *
     * <p>
     * This constructor is intended for DAO row mapping. Unlike the public
     * constructors, it preserves values that are generated or managed by the
     * database, including the assignment ID and creation timestamp.
     * </p>
     *
     * @param id
     *                  database ID of the user-authority assignment
     * @param username
     *                  username receiving the authority
     * @param authority
     *                  authority being assigned
     * @param createdAt
     *                  timestamp when the assignment was created
     * @param grantedBy
     *                  UUID of the user who granted the authority
     * @param expiresAt
     *                  optional expiration timestamp
     */
    public UserAuthority(
            final Integer id,
            final String username,
            final CommonAuthority authority,
            final Instant createdAt,
            final UUID grantedBy,
            final Instant expiresAt) {

        this(username, authority, grantedBy, expiresAt);

        this.setId(id);
        this.createdAt = createdAt;
    }

    /**
     * Get the username receiving this authority.
     *
     * @return username receiving the authority
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Set the username receiving this authority.
     *
     * @param username
     *                 username receiving the authority
     */
    public void setUsername(final String username) {
        this.username = Objects.requireNonNull(
                username,
                "username cannot be null");
    }

    /**
     * Get the authority assigned to the user.
     *
     * @return assigned authority
     */
    public CommonAuthority getAuthority() {
        return this.authority;
    }

    /**
     * Set the authority assigned to the user.
     *
     * @param authority
     *                  authority to assign
     */
    public void setAuthority(final CommonAuthority authority) {
        this.authority = Objects.requireNonNull(
                authority,
                "authority cannot be null");
    }

    /**
     * Get the timestamp at which this authority was assigned.
     *
     * @return assignment timestamp
     */
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Set the assignment timestamp.
     *
     * @param createdAt
     *                  assignment timestamp
     */
    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = Objects.requireNonNull(
                createdAt,
                "createdAt cannot be null");
    }

    /**
     * Get the UUID of the user who granted this authority.
     *
     * @return granting UUID, or {@code null} when system granted
     */
    public UUID getGrantedBy() {
        return this.grantedBy;
    }

    /**
     * Set the UUID of the user who granted this authority.
     *
     * @param grantedBy
     *                  granting UUID, or {@code null} for system grants
     */
    public void setGrantedBy(final UUID grantedBy) {
        this.grantedBy = grantedBy;
    }

    /**
     * Get the expiration timestamp.
     *
     * @return expiration timestamp, or {@code null} if the assignment does not
     *         expire
     */
    public Instant getExpiresAt() {
        return this.expiresAt;
    }

    /**
     * Set the expiration timestamp.
     *
     * @param expiresAt
     *                  expiration timestamp, or {@code null} for no expiration
     */
    public void setExpiresAt(final Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * Determine whether this authority assignment has expired.
     *
     * @return {@code true} when an expiration exists and has passed;
     *         {@code false} otherwise
     */
    public boolean isExpired() {
        return this.expiresAt != null
                && this.expiresAt.isBefore(Instant.now());
    }

    /**
     * Determine whether this authority assignment is currently active.
     *
     * @return {@code true} when the assignment has not expired;
     *         {@code false} otherwise
     */
    public boolean isActive() {
        return !this.isExpired();
    }

    @Override
    public String toString() {
        return "UserAuthority{"
                + "id=" + this.getId()
                + ", username='" + this.username + '\''
                + ", authority=" + this.authority
                + ", createdAt=" + this.createdAt
                + ", grantedBy='" + this.grantedBy + '\''
                + ", expiresAt=" + this.expiresAt
                + '}';
    }

    @Override
    public int compareTo(UserAuthority o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    @Override
    public IModelIdManager<Integer> getIdManager() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdManager'");
    }

}
