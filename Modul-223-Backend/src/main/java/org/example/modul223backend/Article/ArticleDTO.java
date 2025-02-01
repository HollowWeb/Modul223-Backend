package org.example.modul223backend.Article;

import lombok.*;

/**
 * Data Transfer Object (DTO) for the Article entity.
 * Represents the data of an article to be transferred between the client and server.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private String status;
    private Long createdById;
    private String createdByUsername;
    private String createdAt;
    private String updatedAt;
}
