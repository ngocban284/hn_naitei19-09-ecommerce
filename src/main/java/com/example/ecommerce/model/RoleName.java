package com.example.ecommerce.model;

public enum RoleName {
    ADMIN("ADMIN"),
    USER("USER");

    private final String roleName;

    RoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static RoleName getByUpperCaseName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return RoleName.valueOf(name.toUpperCase());
    }
}