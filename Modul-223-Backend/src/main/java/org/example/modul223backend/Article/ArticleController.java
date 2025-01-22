package org.example.modul223backend.Article;

import jakarta.validation.Valid;
import org.example.modul223backend.Article.DTO.ArticleCreateDTO;
import org.example.modul223backend.Article.DTO.ArticleDTO;
import org.example.modul223backend.Article.DTO.ArticleUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
     * Create a new article.
     */
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody @Valid ArticleCreateDTO articleCreateDTO) {
        ArticleDTO createdArticle = articleService.createArticle(articleCreateDTO);
        return ResponseEntity.ok(createdArticle);
    }

    /**
     * Fetch an article by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        ArticleDTO article = articleService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    /**
     * Update an article by ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody @Valid ArticleUpdateDTO articleUpdateDTO) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, articleUpdateDTO);
        return ResponseEntity.ok(updatedArticle);
    }

    /**
     * Soft-delete an article by ID.
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

}
