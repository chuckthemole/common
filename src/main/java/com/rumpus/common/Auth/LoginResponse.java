package com.rumpus.common.Auth;

public class LoginResponse {
    private String message;
    private String username;

    public LoginResponse(String message, String username) {
        this.message = message;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    // Optionally setters if needed
}
