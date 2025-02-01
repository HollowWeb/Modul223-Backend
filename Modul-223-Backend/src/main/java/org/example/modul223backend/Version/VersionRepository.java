package org.example.modul223backend.Version;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query("SELECT v FROM Version v WHERE v.article = :articleId ORDER BY v.versionNumber DESC")
    List<Version> findByArticleIdOrderByVersionNumberDesc(@Param("articleId") Long articleId); // Fetch versions by article, latest first
}

