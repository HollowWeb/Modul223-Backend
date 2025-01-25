package org.example.modul223backend.Tag;

import org.example.modul223backend.Tag.TagDTO;
import org.example.modul223backend.Tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Tag entities.
 * Provides endpoints for creating, retrieving, updating, and deleting tags.
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * Creates a new tag.
     * @param tagDTO the data of the tag to be created.
     * @return the created tag as a DTO.
     */
    @PostMapping
    public TagDTO createTag(@RequestBody TagDTO tagDTO) {
        return tagService.createTag(tagDTO);
    }

    /**
     * Retrieves all tags.
     * @return a list of all tags as DTOs.
     */
    @GetMapping
    public List<TagDTO> getAllTags() {
        return tagService.getAllTags();
    }

    /**
     * Retrieves a specific tag by its ID.
     * @param id the ID of the tag to retrieve.
     * @return the tag as a DTO.
     */
    @GetMapping("/{id}")
    public TagDTO getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    /**
     * Updates an existing tag by its ID.
     * @param id the ID of the tag to update.
     * @param tagDTO the updated data for the tag.
     * @return the updated tag as a DTO.
     */
    @PutMapping("/{id}")
    public TagDTO updateTag(@PathVariable Long id, @RequestBody TagDTO tagDTO) {
        return tagService.updateTag(id, tagDTO);
    }

    /**
     * Deletes a tag by its ID.
     * @param id the ID of the tag to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
