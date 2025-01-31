package org.example.modul223backend.Article;

import org.example.modul223backend.Article.DTO.ArticleCreateDTO;
import org.example.modul223backend.Article.DTO.ArticleDTO;
import org.example.modul223backend.Article.DTO.ArticleUpdateDTO;

import java.util.List;

/**
 * Service interface for managing Article entities.
 * Defines operations for creating, updating, retrieving, and deleting articles.
 */
public interface ArticleService {
    /**
     * Creates a new article.
     * @param articleDTO the data of the article to be created.
     * @return the created article as a DTO.
     */
    ArticleDTO createArticle(ArticleCreateDTO articleCreateDTO);
    /**
     * Retrieves a specific article by its ID.
     * @param id the ID of the article to retrieve.
     * @return the article as a DTO.
     */
    ArticleDTO getArticleById(Long id);
    /**
     * Updates an existing article by its ID.
     * @param id the ID of the article to update.
     * @param articleDTO the updated data for the article.
     * @return the updated article as a DTO.
     */
    ArticleDTO updateArticle(Long id, ArticleUpdateDTO articleUpdateDTO);
    /**
     * Deletes an article by its ID.
     * @param id the ID of the article to delete.
     */
    void deleteArticle(Long id);
    List<ArticleDTO> listArticles(String tag, String status, String search);
    ArticleDTO approveArticle(Long id);
    List<ArticleDTO> getArticlesByUserId(Long userId);
    List<ArticleDTO> getPendingArticles();
    void denyArticle(Long id);
    /**
     * Retrieves all articles.
     * @return a list of all articles as DTOs.
     */
    List<ArticleDTO> getAllArticles();
    /**
     * Retrieves articles by their status (e.g., DRAFT, PUBLISHED, ARCHIVED).
     * @param status the status of the articles to retrieve.
     * @return a list of articles with the specified status as DTOs.
     */
    List<ArticleDTO> getArticlesByStatus(String status);
}
