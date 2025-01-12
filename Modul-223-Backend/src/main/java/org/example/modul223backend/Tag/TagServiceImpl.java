package org.example.modul223backend.Tag;

import org.example.modul223backend.Tag.TagDTO;
import org.example.modul223backend.Tag.Tag;
import org.example.modul223backend.Tag.TagRepository;
import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public TagDTO createTag(TagDTO tagDTO) {
        // Check if tag already exists
        if (tagRepository.existsByTagName(tagDTO.getTagName())) {
            throw new RuntimeException("Tag already exists");
        }

        Tag tag = Mapper.mapToTagEntity(tagDTO);
        tag = tagRepository.save(tag);
        return Mapper.mapToTagDTO(tag);
    }

    @Override
    public List<TagDTO> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(Mapper::mapToTagDTO).collect(Collectors.toList());
    }

    @Override
    public TagDTO getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        return Mapper.mapToTagDTO(tag);
    }

    @Override
    public TagDTO updateTag(Long id, TagDTO tagDTO) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        tag.setTagName(tagDTO.getTagName());
        tag = tagRepository.save(tag);
        return Mapper.mapToTagDTO(tag);
    }

    @Override
    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new RuntimeException("Tag not found");
        }
        tagRepository.deleteById(id);
    }
}

