package org.example.modul223backend.Role;

import lombok.*;

/**
 * Data Transfer Object (DTO) for the Role entity.
 * Represents the data of a role to be transferred between the client and server.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private String roleName; // Role name as string
}
