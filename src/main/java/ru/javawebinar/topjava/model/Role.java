package ru.javawebinar.topjava.model;

import java.util.Arrays;

public enum Role {
    ROLE_USER("User"),
    ROLE_ADMIN("Admin");


    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Role[] getRolesList() {
        return Role.values();
    }

    public static Role getRoleByName(String name) {
        return Arrays.stream(Role.values()).filter(role ->
                role.getName().equals(name)).findFirst().orElse(null);
    }
}