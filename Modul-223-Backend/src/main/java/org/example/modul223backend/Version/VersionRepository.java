package org.example.modul223backend.Version;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VersionRepository extends JpaRepository<Version, Long> {
    @Query("SELECT v FROM Version v WHERE v.article = :articleId ORDER BY v.versionNumber DESC")
    List<Version> findByArticleIdOrderByVersionNumberDesc(@Param("articleId") Long articleId); // Fetch versions by article, latest first
}

