package org.example.modul223backend.Image;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByArticleId(Long articleId); // Get all images linked to an article
}
