package org.example.modul223backend.User.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Includes the specified transferable data which gets send
 * when login an user.
 */
@Data
public class LoginRequestDTO {
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
}
