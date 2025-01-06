package org.example.modul223backend.Article;

import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.Article.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByStatus(Status status);
    List<Article> findByCreatedById(Long userId);
}
