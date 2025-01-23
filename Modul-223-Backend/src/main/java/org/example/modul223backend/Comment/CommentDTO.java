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

}