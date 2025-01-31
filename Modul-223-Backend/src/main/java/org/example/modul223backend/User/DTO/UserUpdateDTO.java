package org.example.modul223backend.User.DTO;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateDTO {
    private String username; // Optional
    @Email
    private String email;    // Optional
    private String password; // Optional
}

