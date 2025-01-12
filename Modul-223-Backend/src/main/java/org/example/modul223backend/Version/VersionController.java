package org.example.modul223backend.Version;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/versions")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @PostMapping
    public VersionDTO createVersion(@RequestBody VersionDTO versionDTO) {
        return versionService.createVersion(versionDTO);
    }

    @GetMapping("/article/{articleId}")
    public List<VersionDTO> getVersionsByArticle(@PathVariable Long articleId) {
        return versionService.getVersionsByArticle(articleId);
    }

    @GetMapping("/{id}")
    public VersionDTO getVersionById(@PathVariable Long id) {
        return versionService.getVersionById(id);
    }
}

