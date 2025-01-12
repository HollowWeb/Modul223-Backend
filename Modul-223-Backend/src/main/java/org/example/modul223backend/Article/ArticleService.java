package org.example.modul223backend.Article;

import org.example.modul223backend.Article.ArticleDTO;
import java.util.List;

public interface ArticleService {
    ArticleDTO createArticle(ArticleDTO articleDTO);
    ArticleDTO updateArticle(Long id, ArticleDTO articleDTO);
    void deleteArticle(Long id);
    ArticleDTO getArticleById(Long id);
    List<ArticleDTO> getAllArticles();
    List<ArticleDTO> getArticlesByStatus(String status);
}
