package org.example.modul223backend.Comment;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId); // Get comments by article
    List<Comment> findByParentCommentId(Long parentCommentId); // Get replies to a comment
}
