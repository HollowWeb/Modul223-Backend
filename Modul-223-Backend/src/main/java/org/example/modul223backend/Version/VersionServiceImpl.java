package org.example.modul223backend.Version;

import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.ArticleRepository;
import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Version entities.
 * Handles the creation, retrieval, and listing of versions.
 */
@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Creates a new version for a specified article.
     * @param versionDTO the data of the version to be created.
     * @return the created version as a DTO.
     */
    @Override
    public VersionDTO createVersion(VersionDTO versionDTO) {
        Article article = articleRepository.findById(versionDTO.getArticleId())
                .orElseThrow(() -> new RuntimeException("Article not found"));

        // Determine the next version number
        int nextVersion = versionRepository.findByArticleIdOrderByVersionNumberDesc(article.getId())
                .stream()
                .mapToInt(Version::getVersionNumber)
                .max()
                .orElse(0) + 1;

        Version version = Mapper.mapToVersionEntity(versionDTO, article, nextVersion);
        version = versionRepository.save(version);

        return Mapper.mapToVersionDTO(version);
    }

    /**
     * Retrieves all versions associated with a specific article, ordered by version number in descending order.
     * @param articleId the ID of the article.
     * @return a list of versions as DTOs.
     */
    @Override
    public List<VersionDTO> getVersionsByArticle(Long articleId) {
        List<Version> versions = versionRepository.findByArticleIdOrderByVersionNumberDesc(articleId);
        return versions.stream().map(Mapper::mapToVersionDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves a specific version by its ID.
     * @param id the ID of the version.
     * @return the version as a DTO.
     */
    @Override
    public VersionDTO getVersionById(Long id) {
        Version version = versionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Version not found"));
        return Mapper.mapToVersionDTO(version);
    }
}

