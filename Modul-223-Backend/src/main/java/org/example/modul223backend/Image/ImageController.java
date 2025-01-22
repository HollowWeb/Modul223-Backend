package org.example.modul223backend.Image;

import org.example.modul223backend.Image.ImageDTO;
import org.example.modul223backend.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {


    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public ImageController(ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    @PostMapping("/{articleId}")
    public ImageDTO uploadImage(@PathVariable(required = false) Long articleId, @RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(articleId, file);
    }

    @GetMapping("/article/{articleId}")
    public List<ImageDTO> getImagesByArticle(@PathVariable Long articleId) {
        return imageService.getImagesByArticle(articleId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageMetadata(@PathVariable Long id) {
        ImageDTO imageDTO = imageService.getImageById(id); // Service returns ImageDTO
        return ResponseEntity.ok(imageDTO);
    }


    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
    }

    @GetMapping("/{id}/content")
    public ResponseEntity<byte[]> getImageContent(@PathVariable Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(image.getMimeType()));
        headers.setContentDisposition(ContentDisposition.inline().filename(image.getFilename()).build());

        return new ResponseEntity<>(image.getImageData(), headers, HttpStatus.OK);
    }

}


