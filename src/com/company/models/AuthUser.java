package com.company.models;

import com.company.Role;

public class AuthUser {
    private final int userId;
    private final String username;
    private final Role role;

    public AuthUser(int userId, String username, Role role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }
}
