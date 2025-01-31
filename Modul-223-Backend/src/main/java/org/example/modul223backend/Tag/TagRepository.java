package org.example.modul223backend.Tag;


import org.example.modul223backend.Tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Tag entities.
 * Provides methods for database interactions related to tags.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    /**
     * Finds a tag by its name.
     * @param tagName the name of the tag to find.
     * @return an Optional containing the tag if found, or empty if not.
     */
    Optional<Tag> findByTagName(String tagName);

    /**
     * Checks if a tag with the given name exists.
     * @param tagName the name of the tag to check.
     * @return true if a tag with the given name exists, false otherwise.
     */
    boolean existsByTagName(String tagName);
}

