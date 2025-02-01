package org.example.modul223backend.Version;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing versions of articles.
 * Provides endpoints for creating, retrieving, and listing versions.
 */
@RestController
@RequestMapping("/api/versions")
public class VersionController {

    @Autowired
    private VersionService versionService;

    /**
     * Creates a new version for an article.
     * @param versionDTO the version data to be created.
     * @return the created version as a DTO.
     */
    @PostMapping
    public VersionDTO createVersion(@RequestBody VersionDTO versionDTO) {
        return versionService.createVersion(versionDTO);
    }

    /**
     * Retrieves all versions associated with a specific article.
     * @param articleId the ID of the article.
     * @return a list of versions as DTOs.
     */
    @GetMapping("/article/{articleId}")
    public List<VersionDTO> getVersionsByArticle(@PathVariable Long articleId) {
        return versionService.getVersionsByArticle(articleId);
    }


    /**
     * Retrieves a specific version by its ID.
     * @param id the ID of the version.
     * @return the version as a DTO.
     */
    @GetMapping("/{id}")
    public VersionDTO getVersionById(@PathVariable Long id) {
        return versionService.getVersionById(id);
    }
}

