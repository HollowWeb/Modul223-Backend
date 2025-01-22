package org.example.modul223backend.Article.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class ArticleCreateDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String content; // Markdown content

    private Set<String> tags; // List of tags
}

