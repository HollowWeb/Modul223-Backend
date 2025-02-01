package org.example.modul223backend.Tag;

import org.example.modul223backend.Tag.TagDTO;

import java.util.List;

/**
 * Service interface for managing Tag entities.
 * Defines operations for creating, retrieving, updating, and deleting tags.
 */
public interface TagService {
    /**
     * Creates a new tag.
     * @param tagDTO the data of the tag to be created.
     * @return the created tag as a DTO.
     */
    TagDTO createTag(TagDTO tagDTO);

    /**
     * Retrieves all tags.
     * @return a list of all tags as DTOs.
     */
    List<TagDTO> getAllTags();

    /**
     * Retrieves a specific tag by its ID.
     * @param id the ID of the tag to retrieve.
     * @return the tag as a DTO.
     */
    TagDTO getTagById(Long id);

    /**
     * Updates an existing tag by its ID.
     * @param id the ID of the tag to update.
     * @param tagDTO the updated data for the tag.
     * @return the updated tag as a DTO.
     */
    TagDTO updateTag(Long id, TagDTO tagDTO);

    /**
     * Deletes a tag by its ID.
     * @param id the ID of the tag to delete.
     */
    void deleteTag(Long id);
}
