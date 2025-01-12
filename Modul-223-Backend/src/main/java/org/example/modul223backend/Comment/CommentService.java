package org.example.modul223backend.Comment;



import java.util.List;

public interface CommentService {
    CommentDTO addComment(CommentDTO commentDTO);
    List<CommentDTO> getCommentsByArticle(Long articleId);
    List<CommentDTO> getReplies(Long parentCommentId);
    void deleteComment(Long id);
}
