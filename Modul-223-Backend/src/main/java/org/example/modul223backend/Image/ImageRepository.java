package org.example.modul223backend.Image;


import org.example.modul223backend.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT i FROM Image i WHERE i.article = :articleId")
    List<Image> findByArticleId(@Param("articleId") long articleId); // Get all images linked to an article
    @Modifying
    @Transactional
    @Query(value = "UPDATE images i " +
            "JOIN articles a ON a.article_id = i.article_id " +
            "SET i.deleted = true " +
            "WHERE a.created_by = :userId", nativeQuery = true)
    void softDeleteByUser(@Param("userId") Long userId);



}
