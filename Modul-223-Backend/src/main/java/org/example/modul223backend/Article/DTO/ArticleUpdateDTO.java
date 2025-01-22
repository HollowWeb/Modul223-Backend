package org.example.modul223backend.Article.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class ArticleUpdateDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String content; // Markdown content

    private String status; // Status (draft, published, archived)

    private Set<String> tags; // List of tags

    public ArticleUpdateDTO(String title, String content, String status, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.tags = tags;
    }
}
