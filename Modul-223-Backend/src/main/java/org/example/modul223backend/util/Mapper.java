package org.example.modul223backend.util;

import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.ArticleDTO;
import org.example.modul223backend.Comment.Comment;
import org.example.modul223backend.Comment.CommentDTO;
import org.example.modul223backend.Image.Image;
import org.example.modul223backend.Image.ImageDTO;
import org.example.modul223backend.Tag.Tag;
import org.example.modul223backend.Tag.TagDTO;
import org.example.modul223backend.User.DTO.UserCreateDTO;
import org.example.modul223backend.User.DTO.UserDTO;
import org.example.modul223backend.Role.*;
import org.example.modul223backend.User.User;
import org.example.modul223backend.Version.Version;
import org.example.modul223backend.Version.VersionDTO;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {

    public static UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet())
        );
    }

    public static User mapToEntity(UserCreateDTO userCreateDTO, Set<Role> roles) {
        return new User(
                null,
                userCreateDTO.getUsername(),
                userCreateDTO.getEmail(),
                userCreateDTO.getPassword(),
                roles,
                null,
                null,
                false
        );
    }

    // Convert Article Entity to DTO
    public static ArticleDTO mapToArticleDTO(Article article) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new ArticleDTO(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                article.getStatus().name(), // Convert ENUM to String
                article.getCreatedBy().getId(),
                article.getCreatedBy().getUsername(),
                article.getCreatedAt().format(formatter),
                article.getUpdatedAt().format(formatter)
        );
    }

    // Convert Article DTO to Entity
    public static Article mapToArticleEntity(ArticleDTO articleDTO, User user) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setStatus(Article.Status.valueOf(articleDTO.getStatus())); // String to ENUM
        article.setCreatedBy(user); // Set the user entity
        return article;
    }

    // Role Mappers
    public static RoleDTO mapToRoleDTO(Role role) {
        return new RoleDTO(role.getId(), role.getRoleName());
    }

    public static Role mapToRoleEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        return role;
    }

    // ============================
    // Tag Mappers
    // ============================

    public static TagDTO mapToTagDTO(Tag tag) {
        return new TagDTO(tag.getId(), tag.getTagName());
    }

    public static Tag mapToTagEntity(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setTagName(tagDTO.getTagName());
        return tag;
    }
    // ============================
    // Image Mappers
    // ============================

    public static ImageDTO mapToImageDTO(Image image) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new ImageDTO(
                image.getId(),
                image.getArticle().getArticleId(),
                image.getFilename(),
                image.getUploadedAt().format(formatter)
        );
    }

    // Comment Mappers
    public static CommentDTO mapToCommentDTO(Comment comment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new CommentDTO(
                comment.getId(),
                comment.getArticle().getArticleId(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getContent(),
                comment.getParentComment() != null ? comment.getParentComment().getId() : null,
                comment.getCreatedAt().format(formatter)
        );
    }

    public static Comment mapToCommentEntity(CommentDTO commentDTO, Article article, User user, Comment parentComment) {
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setUser(user);
        comment.setContent(commentDTO.getContent());
        comment.setParentComment(parentComment);
        return comment;
    }

    // Version Mappers
    public static VersionDTO mapToVersionDTO(Version version) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new VersionDTO(
                version.getId(),
                version.getArticle().getArticleId(),
                version.getContent(),
                version.getVersionNumber(),
                version.getCreatedAt().format(formatter)
        );
    }

    public static Version mapToVersionEntity(VersionDTO versionDTO, Article article, int versionNumber) {
        Version version = new Version();
        version.setArticle(article);
        version.setContent(versionDTO.getContent());
        version.setVersionNumber(versionNumber);
        return version;
    }
}
