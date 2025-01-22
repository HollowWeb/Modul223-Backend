package org.example.modul223backend.Comment;


import org.example.modul223backend.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // only get not deleted comments
    @Query("SELECT c FROM Comment c WHERE c.article.id = :articleId AND c.deleted = false")
    List<Comment> findByArticleId(@Param("articleId") Long articleId);
    @Query("SELECT c FROM Comment c WHERE c.parentComment.id = :parentCommentId AND c.deleted = false")
    List<Comment> findByParentCommentId(@Param("parentCommentId") Long parentCommentId);


    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.deleted = true WHERE c.user = :user")
    void softDeleteByUser(@Param("user") User user);

}
