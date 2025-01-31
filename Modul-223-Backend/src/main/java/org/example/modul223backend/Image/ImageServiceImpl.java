package org.example.modul223backend.Image;



import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.ArticleRepository;
import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ArticleRepository articleRepository;

    public ImageServiceImpl(ImageRepository imageRepository, ArticleRepository articleRepository) {
        this.imageRepository = imageRepository;
        this.articleRepository = articleRepository;
    }


    @Override
    public ImageDTO uploadImage(Long articleId, MultipartFile file) {
        Article article = null;
        if (articleId != null) {
            article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new RuntimeException("Article not found"));
        }

        try {
            Image image = new Image();
            image.setArticle(article);
            image.setFilename(file.getOriginalFilename());
            image.setMimeType(file.getContentType());
            image.setSize(file.getSize());
            image.setImageData(file.getBytes());

            Image savedImage = imageRepository.save(image);

            ImageDTO imageDTO = Mapper.mapToImageDTO(savedImage);
            imageDTO.setUrl(getImageUrl(savedImage.getId())); // Set public URL
            return imageDTO;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    @Override
    public List<ImageDTO> getImagesByArticle(Long articleId) {
        List<Image> images = imageRepository.findByArticleId(articleId);
        return images.stream().map(image -> {
            ImageDTO dto = Mapper.mapToImageDTO(image);
            dto.setUrl(getImageUrl(image.getId()));
            return dto;
        }).toList();
    }

    @Override
    public ImageDTO getImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        ImageDTO dto = Mapper.mapToImageDTO(image);
        dto.setUrl(getImageUrl(image.getId()));
        return dto;
    }

    @Override
    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        image.setDeleted(true);
        imageRepository.save(image);
    }

    @Override
    public String getImageUrl(Long imageId) {
        return Image.getImageUrl(imageId); // Public URL for the image
    }
}


