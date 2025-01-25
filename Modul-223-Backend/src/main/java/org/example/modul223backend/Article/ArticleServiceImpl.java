package org.example.modul223backend.Article;

import org.example.modul223backend.Article.ArticleDTO;
import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.Article.Status;
import org.example.modul223backend.Article.ArticleRepository;
import org.example.modul223backend.User.User;
import org.example.modul223backend.User.UserRepository;
import org.example.modul223backend.exception.Article.ArticleNotFoundException;
import org.example.modul223backend.exception.Article.InvalidArticleDataException;
import org.example.modul223backend.exception.User.UserNotFoundException;
import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Article entities.
 * Handles the business logic for creating, updating, retrieving, and deleting articles.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new article.
     * @param articleDTO the data of the article to be created.
     * @return the created article as a DTO.
     * @throws UserNotFoundException if the user associated with the article is not found.
     * @throws InvalidArticleDataException if the article title is null or empty.
     */
    @Override
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        User user = userRepository.findById(articleDTO.getCreatedById())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (articleDTO.getTitle() == null || articleDTO.getTitle().isEmpty()) {
            throw new InvalidArticleDataException("Title cannot be null or empty");
        }

        Article article = Mapper.mapToArticleEntity(articleDTO, user);
        article = articleRepository.save(article);
        return Mapper.mapToArticleDTO(article);
    }

    /**
     * Updates an existing article by its ID.
     * @param id the ID of the article to update.
     * @param articleDTO the updated data for the article.
     * @return the updated article as a DTO.
     * @throws ArticleNotFoundException if the article with the given ID is not found.
     */
    @Override
    public ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found"));

        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setStatus(Status.valueOf(articleDTO.getStatus()));
        articleRepository.save(article);

        return Mapper.mapToArticleDTO(article);
    }

    /**
     * Deletes an article by its ID.
     * @param id the ID of the article to delete.
     * @throws RuntimeException if the article with the given ID is not found.
     */
    @Override
    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new RuntimeException("Article not found");
        }
        articleRepository.deleteById(id);
    }

    /**
     * Retrieves a specific article by its ID.
     * @param id the ID of the article to retrieve.
     * @return the article as a DTO.
     * @throws RuntimeException if the article with the given ID is not found.
     */
    @Override
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        return Mapper.mapToArticleDTO(article);
    }

    /**
     * Retrieves all articles.
     * @return a list of all articles as DTOs.
     */
    @Override
    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(Mapper::mapToArticleDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves articles by their status (e.g., DRAFT, PUBLISHED, ARCHIVED).
     * @param status the status of the articles to retrieve.
     * @return a list of articles with the specified status as DTOs.
     * @throws IllegalArgumentException if the status is invalid.
     */
    @Override
    public List<ArticleDTO> getArticlesByStatus(String status) {
        List<Article> articles = articleRepository.findByStatus(Status.valueOf(status));
        return articles.stream().map(Mapper::mapToArticleDTO).collect(Collectors.toList());
    }
}
