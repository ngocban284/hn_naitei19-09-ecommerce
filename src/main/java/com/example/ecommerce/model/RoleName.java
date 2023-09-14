package com.example.ecommerce.model;

public enum RoleName {
    ADMIN("Admin"),
    USER("User");

    private final String roleName;

    RoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}