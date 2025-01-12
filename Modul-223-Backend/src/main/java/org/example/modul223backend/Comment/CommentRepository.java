package org.example.modul223backend.Comment;


import org.example.modul223backend.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId); // Get comments by article
    List<Comment> findByParentCommentId(Long parentCommentId); // Get replies to a comment

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.deleted = true WHERE c.user = :user")
    void softDeleteByUser(@Param("user") User user);

}
