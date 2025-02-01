package org.example.modul223backend.Article.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String content; // Markdown content

    private Set<String> tags; // List of tags
}

