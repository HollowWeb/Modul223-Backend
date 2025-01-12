package org.example.modul223backend.Image;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Long id;
    private Long articleId;    // Associated article ID
    private String filename;   // Name of the image file
    private String uploadedAt; // Upload timestamp
}
