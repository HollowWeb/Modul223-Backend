package org.example.modul223backend.Image;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Long id;
    private Long articleId;    // Associated article ID
    private String filename;   // Name of the image file
    private String mimeType;   // Mime type of the image (e.g., "image/png")
    private long size;         // File size in bytes
    private String uploadedAt; // Upload timestamp
    private String url;        // Public URL of the image
}
