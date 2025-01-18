package org.example.modul223backend.User.DTO;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Set;

@Data
public class UserAdminUpdateDTO {
    private String username;
    @Email
    private String email;
    private String password;
    private Set<String> roles;
}

