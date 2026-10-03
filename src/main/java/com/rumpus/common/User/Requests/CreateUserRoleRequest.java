package com.rumpus.common.User.Requests;

import java.time.Instant;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request used to add or remove a user's role")
public class CreateUserRoleRequest {

    @NotBlank
    @Schema(description = "The authority to add or remove", example = "ROLE_ADMIN")
    private String role;

    @Schema(description = "Whether to add or remove the role", example = "ADD")
    private UserRoleOperation operation;

    @Schema(description = "UUID of the user granting the authority", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID grantedBy;

    @Schema(description = "Time at which the authority assignment expires", example = "2027-01-01T00:00:00Z")
    private Instant expiresAt;

    public CreateUserRoleRequest() {

    }

    public String getRole() {

        return this.role;
    }

    public void setRole(String role) {

        this.role = role;
    }

    public UserRoleOperation getOperation() {

        return this.operation;
    }

    public void setOperation(UserRoleOperation operation) {

        this.operation = operation;
    }

    public UUID getGrantedBy() {

        return this.grantedBy;
    }

    public void setGrantedBy(UUID grantedBy) {

        this.grantedBy = grantedBy;
    }

    public Instant getExpiresAt() {

        return this.expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {

        this.expiresAt = expiresAt;
    }
}
