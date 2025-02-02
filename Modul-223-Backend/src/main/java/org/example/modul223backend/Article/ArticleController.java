package org.example.modul223backend.Article;

import jakarta.validation.Valid;
import org.example.modul223backend.Article.DTO.ArticleCreateDTO;
import org.example.modul223backend.Article.DTO.ArticleDTO;
import org.example.modul223backend.Article.DTO.ArticleUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Creates a new article.
     * @param articleCreateDTO the data of the article to be created.
     * @return the created article as a DTO.
     */
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody @Valid ArticleCreateDTO articleCreateDTO) {
        ArticleDTO createdArticle = articleService.createArticle(articleCreateDTO);
        return ResponseEntity.ok(createdArticle);
    }

    /**
     * Retrieves a specific article by its ID.
     * @param id the ID of the article to retrieve.
     * @return the article as a DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        ArticleDTO article = articleService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    /**
     * Updates an existing article by its ID.
     * @param id the ID of the article to update.
     * @param articleUpdateDTO the updated data for the article.
     * @return the updated article as a DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody @Valid ArticleUpdateDTO articleUpdateDTO) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, articleUpdateDTO);
        return ResponseEntity.ok(updatedArticle);
    }

    /**
     * Deletes an article by its ID.
     * @param id the ID of the article to delete.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok("Article deleted successfully.");
    }

    /**
     * List articles with optional filters.
     */
    @GetMapping
    public ResponseEntity<List<ArticleDTO>> listArticles(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "search", required = false) String search) {
        List<ArticleDTO> articles = articleService.listArticles(tag, status, search);
        return ResponseEntity.ok(articles);
    }

    @PutMapping("/{id}/request-approval")
    public ResponseEntity<ArticleDTO> requestApproval(@PathVariable Long id) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, new ArticleUpdateDTO(null, null, "PENDING_APPROVAL", null));
        return ResponseEntity.ok(updatedArticle);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<ArticleDTO> approveArticle(@PathVariable Long id) {
        ArticleDTO approvedArticle = articleService.approveArticle(id);
        return ResponseEntity.ok(approvedArticle);
    }

    /**
     * Get all articles created by a specific user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ArticleDTO>> getArticlesByUserId(@PathVariable Long userId) {
        List<ArticleDTO> articles = articleService.getArticlesByUserId(userId);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ArticleDTO>> getPendingArticles() {
        List<ArticleDTO> articles = articleService.getPendingArticles();
        return ResponseEntity.ok(articles);
    }

    @PutMapping("/{id}/deny")
    public HttpStatus denyArticle(@PathVariable Long id) {
        articleService.denyArticle(id);
        return HttpStatus.OK;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }


}
