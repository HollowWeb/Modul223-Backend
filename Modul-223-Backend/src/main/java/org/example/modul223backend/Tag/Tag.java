package org.example.modul223backend.Tag;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing a Tag.
 * Tags are used to categorize or label articles or other entities.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tagName;
}
