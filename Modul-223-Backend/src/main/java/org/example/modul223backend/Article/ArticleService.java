package org.example.modul223backend.Article;

import org.example.modul223backend.Article.DTO.ArticleCreateDTO;
import org.example.modul223backend.Article.DTO.ArticleDTO;
import org.example.modul223backend.Article.DTO.ArticleUpdateDTO;

import java.util.List;

public interface ArticleService {
    ArticleDTO createArticle(ArticleCreateDTO articleCreateDTO);
    ArticleDTO getArticleById(Long id);
    ArticleDTO updateArticle(Long id, ArticleUpdateDTO articleUpdateDTO);
    void deleteArticle(Long id);
    List<ArticleDTO> listArticles(String tag, String status, String search);
    ArticleDTO approveArticle(Long id);
    List<ArticleDTO> getArticlesByUserId(Long userId);
    List<ArticleDTO> getPendingArticles();
    void denyArticle(Long id);

}
