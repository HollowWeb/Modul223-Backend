package org.example.modul223backend.util;

import org.example.modul223backend.User.User;

public class UserUtils {
    public static boolean hasRole(User user, String roleName) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(roleName));
    }
}

