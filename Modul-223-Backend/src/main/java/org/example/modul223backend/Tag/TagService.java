package org.example.modul223backend.Tag;

import org.example.modul223backend.Tag.TagDTO;

import java.util.List;

public interface TagService {
    TagDTO createTag(TagDTO tagDTO);
    List<TagDTO> getAllTags();
    TagDTO getTagById(Long id);
    TagDTO updateTag(Long id, TagDTO tagDTO);
    void deleteTag(Long id);
}
