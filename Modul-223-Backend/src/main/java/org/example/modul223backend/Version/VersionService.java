package org.example.modul223backend.Version;

import java.util.List;

/**
 * Service interface for managing Version entities.
 * Defines operations for creating, retrieving, and listing versions.
 */
public interface VersionService {
    /**
     * Creates a new version for an article.
     * @param versionDTO the data of the version to be created.
     * @return the created version as a DTO.
     */
    VersionDTO createVersion(VersionDTO versionDTO);

    /**
     * Retrieves all versions associated with a specific article.
     * @param articleId the ID of the article.
     * @return a list of versions as DTOs.
     */
    List<VersionDTO> getVersionsByArticle(Long articleId);

    /**
     * Retrieves a specific version by its ID.
     * @param id the ID of the version.
     * @return the version as a DTO.
     */
    VersionDTO getVersionById(Long id);
}

