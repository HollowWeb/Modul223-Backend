package org.example.modul223backend.Image;

import org.example.modul223backend.Image.ImageDTO;
import org.example.modul223backend.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/{articleId}")
    public ImageDTO uploadImage(@PathVariable Long articleId, @RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(articleId, file);
    }

    @GetMapping("/article/{articleId}")
    public List<ImageDTO> getImagesByArticle(@PathVariable Long articleId) {
        return imageService.getImagesByArticle(articleId);
    }

    @GetMapping("/{id}")
    public ImageDTO getImageById(@PathVariable Long id) {
        return imageService.getImageById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
    }
}

