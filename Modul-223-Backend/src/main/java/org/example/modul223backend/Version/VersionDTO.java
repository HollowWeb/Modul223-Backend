package org.example.modul223backend.Version;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionDTO {
    private Long id;
    private Long articleId;
    private String content;
    private int versionNumber;
    private String createdAt; // Formatted date
}

