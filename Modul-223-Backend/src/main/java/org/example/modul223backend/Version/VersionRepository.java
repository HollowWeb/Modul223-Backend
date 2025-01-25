package org.example.modul223backend.Version;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Version entities.
 * Provides methods for database interactions related to versions.
 */
public interface VersionRepository extends JpaRepository<Version, Long> {

    /**
     * Retrieves all versions for a specific article, ordered by version number in descending order.
     * @param articleId the ID of the article.
     * @return a list of versions associated with the article, sorted by version number (latest first).
     */
    List<Version> findByArticleIdOrderByVersionNumberDesc(Long articleId); // Fetch versions by article, latest first
}

