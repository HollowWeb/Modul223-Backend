package org.example.modul223backend.Image;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ImageDTO uploadImage(Long articleId, MultipartFile file);
    List<ImageDTO> getImagesByArticle(Long articleId);
    ImageDTO getImageById(Long id);
    void deleteImage(Long id);
    String getImageUrl(Long imageId); // Generate a public URL for the image
}
