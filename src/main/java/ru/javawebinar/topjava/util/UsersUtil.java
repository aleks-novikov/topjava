package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.*;

public class UsersUtil {

    public static final List<User> USERS = Arrays.asList(
            new User(null, "Alex", "alex@example.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER),
            new User(null, "John", "john@example.com", "Pass123!", Role.ROLE_USER),
            new User(null, "Michael", "michael@example.com", "fakkadf123", Role.ROLE_USER));

    public static String getStrRoles(Set<Role> roles) {
        StringBuilder sb = new StringBuilder();

        roles.forEach(role -> sb.append(role.getName()).append(", "));
        return sb.toString().substring(0, sb.length() - 2);
    }
}