package com.rumpus.common.User;

import java.util.Objects;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.rumpus.common.Model.AbstractModel;
import com.rumpus.common.Model.IModelIdManager;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

/**
 * Common implementation of a Spring Security {@link GrantedAuthority}.
 *
 * <p>
 * A {@code CommonAuthority} represents a single authority granted to a user,
 * such as {@code ROLE_USER}, {@code ROLE_ADMIN}, or {@code READ_REPORTS}.
 * </p>
 *
 * <p>
 * This class is intentionally independent of persistence. Database operations
 * for creating, deleting, or querying authorities belong in the appropriate
 * service/DAO layer rather than in the authority model itself.
 * </p>
 */
@Table(name = "authority")
@Schema(description = "CommonAuthority - implementation of Spring Security's GrantedAuthority interface, representing a single authority granted to a user")
public class CommonAuthority extends AbstractModel<CommonAuthority, UUID>
        implements
        GrantedAuthority {
    // TODO: Eventually remove GrantedAuthority from CommonAuthority.
    // Keep this class focused on the persisted/domain authority model and
    // convert to Spring Security's SimpleGrantedAuthority at the security boundary.

    @Column(name = "authority")
    @Schema(description = "The name of the authority", example = "ROLE_USER")
    private final String authority;

    /**
     * Creates an authority from its string representation.
     *
     * @param authority
     *                  the authority name; must not be null or blank
     * @throws IllegalArgumentException
     *                                  if the authority is null or blank
     */
    public CommonAuthority(UUID id, String authority) {
        if (authority == null || authority.isBlank()) {
            throw new IllegalArgumentException("Authority must not be null or blank");
        }
        super.setId(id);
        this.authority = authority;
    }

    /**
     * Creates an authority from another {@link GrantedAuthority}.
     *
     * @param authority
     *                  the authority to copy; must not be null
     * @throws IllegalArgumentException
     *                                  if the authority is null or has a null or
     *                                  blank name
     */
    public CommonAuthority(UUID id, GrantedAuthority authority) {
        this(
                id,
                Objects
                        .requireNonNull(authority, "Authority must not be null")
                        .getAuthority());
    }

    /**
     * Returns the name of this authority.
     *
     * <p>
     * Spring Security uses this value when determining whether a user has
     * permission to access a protected resource.
     * </p>
     *
     * @return the authority name
     */
    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public int compareTo(CommonAuthority o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    @Override
    public IModelIdManager<UUID> getIdManager() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdManager'");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CommonAuthority other)) {
            return false;
        }

        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    @Override
    public String toString() {
        return "CommonAuthority{" +
                "id=" + this.getId() +
                ", authority='" + authority + '\'' +
                '}';
    }
}
