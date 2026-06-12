package com.rumpus.common.User.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request used to create a new user")
public class CreateUserRequest {

    @NotBlank
    @Schema(description = "Unique username", example = "chuck")
    private String username;

    @NotBlank
    @Schema(description = "User password", example = "MySecurePassword123!")
    private String password;

    @NotBlank
    @Schema(description = "User email", example = "chuck@example.com")
    private String email;

    public CreateUserRequest() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
