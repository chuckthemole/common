package com.rumpus.common.User.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request used to update a user's roles")
public class CreateUserRoleRequest {

    @NotBlank
    @Schema(description = "The role to add or remove", example = "ROLE_ADMIN")
    private String role;

    @Schema(description = "Whether to add or remove the role", example = "ADD")
    private UserRoleOperation operation;

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
}