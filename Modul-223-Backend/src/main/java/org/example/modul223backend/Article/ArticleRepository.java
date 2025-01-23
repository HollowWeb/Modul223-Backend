package org.example.modul223backend.Article;

import org.example.modul223backend.Article.Article.Status;
import org.example.modul223backend.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByStatus(Status status);
    List<Article> findByCreatedById(Long userId);

    @Query("SELECT a FROM Article a WHERE a.deleted = false")
    List<Article> findAllActive();

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.deleted = true WHERE a.createdBy = :user")
    void softDeleteByUser(@Param("user") User user);

    @Query("SELECT a FROM Article a JOIN a.tags t WHERE t.tagName = :tagName")
    List<Article> findByTagName(@Param("tagName") String tagName);

    List<Article> searchByTitle(String title);
}
