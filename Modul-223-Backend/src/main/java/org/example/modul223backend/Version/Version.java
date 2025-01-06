package org.example.modul223backend.Version;

import jakarta.persistence.*;
import lombok.*;
import org.example.modul223backend.Article.Article;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "versions")
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false) // Links to the Article
    private Article article;

    @Column(columnDefinition = "TEXT")
    private String content; // Content in Markdown format

    @Column(nullable = false)
    private int versionNumber; // Tracks version numbers

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

