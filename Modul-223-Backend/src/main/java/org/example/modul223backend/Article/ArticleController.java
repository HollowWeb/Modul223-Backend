package org.example.modul223backend.Article;

import org.example.modul223backend.Article.ArticleDTO;
import org.example.modul223backend.Article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing Article entities.
 * Provides endpoints for creating, retrieving, updating, and deleting articles.
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * Creates a new article.
     * @param articleDTO the data of the article to be created.
     * @return the created article as a DTO.
     */
    @PostMapping
    public ArticleDTO createArticle(@RequestBody ArticleDTO articleDTO) {
        return articleService.createArticle(articleDTO);
    }

    /**
     * Retrieves a specific article by its ID.
     * @param id the ID of the article to retrieve.
     * @return the article as a DTO.
     */
    @GetMapping("/{id}")
    public ArticleDTO getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    /**
     * Retrieves all articles.
     * @return a list of all articles as DTOs.
     */
    @GetMapping("/all")
    public List<ArticleDTO> getAllArticles() {
        return articleService.getAllArticles();
    }

    /**
     * Retrieves articles by their status (e.g., DRAFT, PUBLISHED, ARCHIVED).
     * @param status the status of the articles to retrieve.
     * @return a list of articles with the specified status as DTOs.
     */
    @GetMapping("/status/{status}")
    public List<ArticleDTO> getArticlesByStatus(@PathVariable String status) {
        return articleService.getArticlesByStatus(status);
    }

    /**
     * Updates an existing article by its ID.
     * @param id the ID of the article to update.
     * @param articleDTO the updated data for the article.
     * @return the updated article as a DTO.
     */
    @PutMapping("/{id}")
    public ArticleDTO updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        return articleService.updateArticle(id, articleDTO);
    }

    /**
     * Deletes an article by its ID.
     * @param id the ID of the article to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }
}
