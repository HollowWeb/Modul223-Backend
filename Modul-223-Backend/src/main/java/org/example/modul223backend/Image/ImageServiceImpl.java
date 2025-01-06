package org.example.modul223backend.Image;



import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.ArticleRepository;
import org.example.modul223backend.Image.ImageDTO;
import org.example.modul223backend.Image.Image;
import org.example.modul223backend.Image.ImageRepository;
import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public ImageDTO uploadImage(Long articleId, MultipartFile file) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        try {
            Image image = new Image();
            image.setArticle(article);
            image.setFilename(file.getOriginalFilename());
            image.setImageData(file.getBytes());

            Image savedImage = imageRepository.save(image);
            return Mapper.mapToImageDTO(savedImage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    @Override
    public List<ImageDTO> getImagesByArticle(Long articleId) {
        List<Image> images = imageRepository.findByArticleId(articleId);
        return images.stream().map(Mapper::mapToImageDTO).collect(Collectors.toList());
    }

    @Override
    public ImageDTO getImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        return Mapper.mapToImageDTO(image);
    }

    @Override
    public void deleteImage(Long id) {
        if (!imageRepository.existsById(id)) {
            throw new RuntimeException("Image not found");
        }
        imageRepository.deleteById(id);
    }
}

