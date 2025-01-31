package org.example.modul223backend.User.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.modul223backend.Role.Role;

import java.util.HashSet;
import java.util.Set;

/**
 * Includes the transferable data for the user creation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private Set<Role> roles = new HashSet<>();

    public UserCreateDTO(String testUser, String mail, String password123) {
        this.username = testUser;
        this.email = mail;
        this.password = password123;
    }
}
