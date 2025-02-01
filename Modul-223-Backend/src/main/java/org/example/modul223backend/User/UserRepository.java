package org.example.modul223backend.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Responsible for the user database handling
 * Which specific data query requests.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    /**
     * Get all active (non-deleted) users.
     */
    @Query("SELECT u FROM User u WHERE u.deleted = false")
    List<User> findAllActive();

    /**
     * Get an active (non-deleted) user by their ID.
     */
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.deleted = false")
    Optional<User> findActiveById(@Param("id") Long id);

    /**
     * Find a user by their username, excluding deleted users.
     */
    Optional<User> findByUsernameAndDeletedFalse(String username);

    /**
     * Search for users based on username, email, and role.
     * Excludes deleted users.
     */
    @Query("SELECT u FROM User u " +
            "WHERE (:username IS NULL OR u.username LIKE %:username%) " +
            "AND (:email IS NULL OR u.email LIKE %:email%) " +
            "AND (:role IS NULL OR EXISTS (SELECT r FROM u.roles r WHERE r.roleName = :role)) " +
            "AND u.deleted = false")
    List<User> searchUsers(
            @Param("username") String username,
            @Param("email") String email,
            @Param("role") String role);

}
