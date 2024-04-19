package com.authorization.config;

public enum Roles {
    ADMIN("ROLE_ADMIN"),
    EMPLOYEE("ROLE_EMPLOYEE"),
    USER("ROLE_USER");

    private final String asRole;

    Roles(String asRole){
        this.asRole = asRole;
    }

    public String getAsRole() {
        return asRole;
    }
}
