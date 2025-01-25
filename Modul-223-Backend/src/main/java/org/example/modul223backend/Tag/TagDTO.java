package org.example.modul223backend.Tag;

import lombok.*;

/**
 * Data Transfer Object (DTO) for the Tag entity.
 * Represents the data of a tag to be transferred between the client and server.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private Long id;
    private String tagName; // Name of the tag
}

