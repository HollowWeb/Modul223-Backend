package org.example.modul223backend.Comment;

import jakarta.persistence.*;
import lombok.*;
import org.example.modul223backend.Article.Article;
import org.example.modul223backend.User.User;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article; // Associated article

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User who created the comment

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // Comment content

    @ManyToOne
    @JoinColumn(name = "parent_comment_id") // Optional parent comment for replies
    private Comment parentComment;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

