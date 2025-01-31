package org.example.modul223backend.Version;

import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.ArticleRepository;
import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public VersionDTO createVersion(VersionDTO versionDTO) {
        Article article = articleRepository.findById(versionDTO.getArticleId())
                .orElseThrow(() -> new RuntimeException("Article not found"));

        // Determine the next version number
        int nextVersion = versionRepository.findByArticleIdOrderByVersionNumberDesc(article.getArticleId())
                .stream()
                .mapToInt(Version::getVersionNumber)
                .max()
                .orElse(0) + 1;

        Version version = Mapper.mapToVersionEntity(versionDTO, article, nextVersion);
        version = versionRepository.save(version);

        return Mapper.mapToVersionDTO(version);
    }

    @Override
    public List<VersionDTO> getVersionsByArticle(Long articleId) {
        List<Version> versions = versionRepository.findByArticleIdOrderByVersionNumberDesc(articleId);
        return versions.stream().map(Mapper::mapToVersionDTO).collect(Collectors.toList());
    }

    @Override
    public VersionDTO getVersionById(Long id) {
        Version version = versionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Version not found"));
        return Mapper.mapToVersionDTO(version);
    }
}

