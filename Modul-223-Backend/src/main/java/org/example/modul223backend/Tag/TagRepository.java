package org.example.modul223backend.Tag;


import org.example.modul223backend.Tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String tagName);
    boolean existsByTagName(String tagName);
}

