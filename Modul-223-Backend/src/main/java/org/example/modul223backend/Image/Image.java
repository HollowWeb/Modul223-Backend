package org.example.modul223backend.Image;

import jakarta.persistence.*;
import lombok.*;
import org.example.modul223backend.Article.Article;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false) // Foreign key to Article
    private Article article;

    @Column(nullable = false)
    private String filename; // Name of the image file

    @Lob
    @Column(nullable = false)
    private byte[] imageData; // Binary data for the image

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
    }
}
