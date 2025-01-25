package org.example.modul223backend.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Role entities.
 * Provides methods for database interactions related to roles.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name.
     * @param roleName the name of the role to find.
     * @return an Optional containing the role if found, or empty if not.
     */
    Optional<Role> findByRoleName(String roleName);

    /**
     * Checks if a role with the given name exists.
     * @param roleName the name of the role to check.
     * @return true if a role with the given name exists, false otherwise.
     */
    boolean existsByRoleName(String roleName);
}

