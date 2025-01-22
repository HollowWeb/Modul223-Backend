package org.example.modul223backend.Comment;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private Long articleId;
    private Long userId;
    private String username; // Username of commenter
    private String content;
    private Long parentCommentId; // Optional parent comment
    private boolean isReply;
    private String createdAt;

    public CommentDTO(Long id, Long articleId, Long userId, String username, String content, Long parentCommentId, boolean isReply, String createdAt) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.parentCommentId = parentCommentId;
        this.isReply = isReply;
        this.createdAt = createdAt;
    }
}