package org.example.modul223backend.Version;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionRepository extends JpaRepository<Version, Long> {
    List<Version> findByArticleIdOrderByVersionNumberDesc(Long articleId); // Fetch versions by article, latest first
}

