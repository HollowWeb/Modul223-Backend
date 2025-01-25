package org.example.modul223backend.Article;

import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.Article.Status;
import org.example.modul223backend.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository interface for managing Article entities.
 * Provides methods for querying and modifying article data in the database.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * Finds articles by their status (e.g., DRAFT, PUBLISHED, ARCHIVED).
     * @param status the status of the articles to retrieve.
     * @return a list of articles with the specified status.
     */
    List<Article> findByStatus(Status status);

    /**
     * Finds articles created by a specific user.
     * @param userId the ID of the user who created the articles.
     * @return a list of articles created by the specified user.
     */
    List<Article> findByCreatedById(Long userId);

    /**
     * Retrieves all active articles that have not been marked as deleted.
     * @return a list of active articles.
     */
    @Query("SELECT a FROM Article a WHERE a.deleted = false")
    List<Article> findAllActive();

    /**
     * Soft deletes articles created by a specific user.
     * Marks the articles as deleted without physically removing them from the database.
     * @param user the user whose articles are to be soft-deleted.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.deleted = true WHERE a.createdBy = :user")
    void softDeleteByUser(@Param("user") User user);

}
