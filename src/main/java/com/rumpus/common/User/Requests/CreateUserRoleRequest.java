package com.rumpus.common.User.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request used to update a user's role")
public class CreateUserRoleRequest {

    @NotBlank
    @Schema(description = "The new role for the user", example = "ROLE_ADMIN")
    private String role;

    public CreateUserRoleRequest() {
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
