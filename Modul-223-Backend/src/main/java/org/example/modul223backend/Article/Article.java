package org.example.modul223backend.Article;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content; // Stored in Markdown

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private org.example.modul223backend.User.User createdBy;

    @Enumerated(EnumType.STRING)
    private Status status; // Enum for draft, published, archived

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Long getId() {
        return articleId;
    }

    public enum Status {
        DRAFT,
        PUBLISHED,
        ARCHIVED
    }

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

