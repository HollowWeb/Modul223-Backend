package org.example.modul223backend.Article;

import org.example.modul223backend.Article.DTO.ArticleCreateDTO;
import org.example.modul223backend.Article.DTO.ArticleDTO;
import org.example.modul223backend.Article.DTO.ArticleUpdateDTO;
import org.example.modul223backend.Role.RoleConstants;
import org.example.modul223backend.Tag.Tag;
import org.example.modul223backend.Tag.TagRepository;
import org.example.modul223backend.User.User;
import org.example.modul223backend.User.UserRepository;
import org.example.modul223backend.exception.Article.ArticleNotFoundException;
import org.example.modul223backend.exception.ErrorMessages;
import org.example.modul223backend.exception.User.UserNotFoundException;
import org.example.modul223backend.exception.UserException.UnauthorizedActionException;
import org.example.modul223backend.util.Mapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Article entities.
 * Handles the business logic for creating, updating, retrieving, and deleting articles.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, TagRepository tagRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public ArticleDTO createArticle(ArticleCreateDTO articleCreateDTO) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsernameAndDeletedFalse(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user not found."));

        // Map tags to existing
        Set<Tag> tags = articleCreateDTO.getTags() != null
                ? articleCreateDTO.getTags().stream()
                .map(tagName -> tagRepository.findByTagName(tagName)
                        .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.TAG_NOT_FOUND + ": " + tagName)))
                .collect(Collectors.toSet())
                : Collections.emptySet();

        // Create the article
        Article article = new Article();
        article.setTitle(articleCreateDTO.getTitle());
        article.setContent(articleCreateDTO.getContent());
        article.setCreatedBy(currentUser);
        article.setStatus(Article.Status.DRAFT); // Default status
        article.setDeleted(false);

        // Save the article and associate tags
        Article savedArticle = articleRepository.save(article);
        savedArticle.setTags(tags);

        return Mapper.mapToArticleDTO(savedArticle);
    }

    /**
     * Creates a new article.
     * @param articleDTO the data of the article to be created.
     * @return the created article as a DTO.
     * @throws UserNotFoundException if the user associated with the article is not found.
     * @throws InvalidArticleDataException if the article title is null or empty.
     */
    @Override
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found."));
        return Mapper.mapToArticleDTO(article);
    }
    /**
     * Updates an existing article by its ID.
     * @param id the ID of the article to update.
     * @param articleDTO the updated data for the article.
     * @return the updated article as a DTO.
     * @throws ArticleNotFoundException if the article with the given ID is not found.
     */
    @Transactional
    @Override
    public ArticleDTO updateArticle(Long id, ArticleUpdateDTO articleUpdateDTO) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found."));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsernameAndDeletedFalse(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user not found."));

        boolean isAdminOrEditor = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleConstants.ADMIN) ||
                        role.getRoleName().equals(RoleConstants.EDITOR));

        // Only the creator or an admin/editor can update the article
        if (!isAdminOrEditor && !article.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new UnauthorizedActionException("You are not authorized to update this article.");
        }

        // Update article fields
        if (articleUpdateDTO.getTitle() != null) {
            article.setTitle(articleUpdateDTO.getTitle());
        }
        if (articleUpdateDTO.getContent() != null) {
            article.setContent(articleUpdateDTO.getContent());
        }
        if (articleUpdateDTO.getTags() != null) {
            Set<Tag> tags = articleUpdateDTO.getTags().stream()
                    .map(tagName -> tagRepository.findByTagName(tagName)
                            .orElseThrow(() -> new IllegalArgumentException("Tag not found: " + tagName)))
                    .collect(Collectors.toSet());
            article.setTags(tags);
        }

        // Handle status change
        if (articleUpdateDTO.getStatus() != null) {
            Article.Status requestedStatus = Article.Status.valueOf(articleUpdateDTO.getStatus().toUpperCase());

            if (requestedStatus == Article.Status.PUBLISHED) {
                // Only admins or editors can publish
                if (!isAdminOrEditor) {
                    throw new UnauthorizedActionException("Only admins or editors can publish articles.");
                }
            } else if (requestedStatus == Article.Status.PENDING_APPROVAL) {
                // Creator can request approval
                article.setStatus(Article.Status.PENDING_APPROVAL);
            } else {
                // Allow other status updates (e.g., DRAFT or ARCHIVED)
                article.setStatus(requestedStatus);
            }
        }

        // Save updated article
        Article updatedArticle = articleRepository.save(article);
        return Mapper.mapToArticleDTO(updatedArticle);
    }


    @Transactional
    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found."));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsernameAndDeletedFalse(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user not found."));

        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleConstants.ADMIN));

        // Only the creator or an admin can delete the article
        if (!isAdmin && !article.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new UnauthorizedActionException("You are not authorized to delete this article.");
        }

        // Soft delete
        article.setDeleted(true);
        articleRepository.save(article);
    }

    @Override
    public List<ArticleDTO> listArticles(String tag, String status, String search) {
        List<Article> articles;

        if (tag != null) {
            articles = articleRepository.findByTagName(tag);
        } else if (status != null) {
            articles = articleRepository.findByStatus(Article.Status.valueOf(status.toUpperCase()));
        } else if (search != null) {
            articles = articleRepository.searchByTitle(search);
        } else {
            articles = articleRepository.findAllActive();
        }

        return articles.stream().map(Mapper::mapToArticleDTO).toList();
    }

    @Transactional
    @Override
    public ArticleDTO approveArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found."));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsernameAndDeletedFalse(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user not found."));

        boolean isAdminOrEditor = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleConstants.ADMIN) ||
                        role.getRoleName().equals(RoleConstants.EDITOR));

        if (!isAdminOrEditor) {
            throw new UnauthorizedActionException("Only admins or editors can approve articles.");
        }

        if (article.getStatus() != Article.Status.PENDING_APPROVAL) {
            throw new IllegalArgumentException("Only articles with status 'PENDING_APPROVAL' can be approved.");
        }

        article.setStatus(Article.Status.PUBLISHED);
        article = articleRepository.save(article);
        return Mapper.mapToArticleDTO(article);
    }

    /**
     * Deletes an article by its ID.
     * @param id the ID of the article to delete.
     * @throws RuntimeException if the article with the given ID is not found.
     */
    @Override
    public List<ArticleDTO> getArticlesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        // Retrieve articles created by the user and not deleted
        List<Article> articles = articleRepository.findByCreatedById(userId)
                .stream()
                .filter(article -> !article.isDeleted())
                .toList();

        return articles.stream().map(Mapper::mapToArticleDTO).toList();
    }

    /**
     * Retrieves a specific article by its ID.
     * @param id the ID of the article to retrieve.
     * @return the article as a DTO.
     * @throws RuntimeException if the article with the given ID is not found.
     */
    @Override
    public List<ArticleDTO> getPendingArticles() {
        return  articleRepository.findByStatus(Article.Status.PENDING_APPROVAL).stream().map(Mapper::mapToArticleDTO).toList();
    }

    @Override
    public void denyArticle(Long id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found."));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsernameAndDeletedFalse(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user not found."));

        boolean isAdminOrEditor = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleConstants.ADMIN) ||
                        role.getRoleName().equals(RoleConstants.EDITOR));

        if (!isAdminOrEditor) {
            throw new UnauthorizedActionException("Only admins or editors can deny articles.");
        }

        if (article.getStatus() != Article.Status.PENDING_APPROVAL) {
            throw new IllegalArgumentException("Only articles with status 'PENDING_APPROVAL' can be denied.");
        }

        article.setStatus(Article.Status.DENIED);
        articleRepository.save(article);
    }

    /**
     * Retrieves all articles.
     * @return a list of all articles as DTOs.
     */
    @Override
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAllActiveAndPublished().stream().map(Mapper::mapToArticleDTO).toList();
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
