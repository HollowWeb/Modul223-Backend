package org.example.modul223backend.User;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.example.modul223backend.Role.Role;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

