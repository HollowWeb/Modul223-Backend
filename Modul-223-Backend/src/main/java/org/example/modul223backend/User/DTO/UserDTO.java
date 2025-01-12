package org.example.modul223backend.User.DTO;

import jakarta.validation.constraints.Email;
import lombok.*;


import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    @Email
    private String email;
    private Set<String> roles; // Role as a string

}

