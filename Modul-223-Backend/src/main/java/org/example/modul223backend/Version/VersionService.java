package org.example.modul223backend.Version;

import java.util.List;

public interface VersionService {
    VersionDTO createVersion(VersionDTO versionDTO);
    List<VersionDTO> getVersionsByArticle(Long articleId);
    VersionDTO getVersionById(Long id);
}

