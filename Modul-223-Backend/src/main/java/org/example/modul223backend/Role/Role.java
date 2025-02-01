package org.example.modul223backend.Role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.modul223backend.User.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a Role.
 * Roles are used to define user permissions and access levels.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {

    /**
     * The unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the role (e.g., "ADMIN", "USER").
     * Must be unique and cannot be null.
     */
    @Getter
    @Column(nullable = false, unique = true)
    private String roleName;
}
