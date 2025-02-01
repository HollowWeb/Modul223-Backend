package org.example.modul223backend.util;

import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.DTO.ArticleDTO;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for mapping entities to DTOs and vice versa.
 */
public class Mapper {

    private final  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * Maps a User entity to a UserDTO.
     * @param user the User entity.
     * @return the mapped UserDTO.
     */
    public static UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet())
        );
    }

    /**
     * Maps a UserCreateDTO and roles to a User entity.
     * @param userCreateDTO the UserCreateDTO.
     * @param roles the set of roles.
     * @return the mapped User entity.
     */
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

    /**
     * Maps an Article entity to an ArticleDTO.
     * @param article the Article entity.
     * @return the mapped ArticleDTO.
     */
    public static ArticleDTO mapToArticleDTO(Article article) {
        if (article == null) {
            throw new IllegalArgumentException("Article cannot be null");
        }

        if (article.getCreatedBy() == null) {
            throw new IllegalArgumentException("Article creator (User) cannot be null");
        }

        Set<String> tagNames = article.getTags()
                .stream()
                .map(Tag::getTagName) // Extract only the tagName
                .collect(Collectors.toSet());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new ArticleDTO(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                article.getStatus() != null ? article.getStatus().name() : null, // Handle null ENUM
                tagNames,
                article.getCreatedAt() != null ? LocalDateTime.parse(article.getCreatedAt().format(formatter), formatter) : null, // Handle potential nulls
                article.getUpdatedAt() != null ? LocalDateTime.parse(article.getUpdatedAt().format(formatter), formatter) : null,  // Handle potential nulls
                article.getCreatedBy().getUsername()
                );
    }


    /**
     * Maps an ArticleDTO to an Article entity.
     * @param articleDTO the ArticleDTO.
     * @param user the User entity creating the article.
     * @return the mapped Article entity.
     */
    public static Article mapToArticleEntity(ArticleDTO articleDTO, User user) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setStatus(Article.Status.valueOf(articleDTO.getStatus())); // String to ENUM
        article.setCreatedBy(user); // Set the user entity
        return article;
    }

    /**
     * Maps a Role entity to a RoleDTO.
     * @param role the Role entity.
     * @return the mapped RoleDTO.
     */
    public static RoleDTO mapToRoleDTO(Role role) {
        return new RoleDTO(role.getId(), role.getRoleName());
    }

    /**
     * Maps a RoleDTO to a Role entity.
     * @param roleDTO the RoleDTO.
     * @return the mapped Role entity.
     */
    public static Role mapToRoleEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        return role;
    }

    /**
     * Maps a Tag entity to a TagDTO.
     * @param tag the Tag entity.
     * @return the mapped TagDTO.
     */
    public static TagDTO mapToTagDTO(Tag tag) {
        return new TagDTO(tag.getId(), tag.getTagName());
    }

    /**
     * Maps a TagDTO to a Tag entity.
     * @param tagDTO the TagDTO.
     * @return the mapped Tag entity.
     */
    public static Tag mapToTagEntity(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setTagName(tagDTO.getTagName());
        return tag;
    }

    /**
     * Maps an Image entity to an ImageDTO.
     * @param image the Image entity.
     * @return the mapped ImageDTO.
     */
    public static ImageDTO mapToImageDTO(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }

        if (image.getArticle() == null) {
            throw new IllegalArgumentException("Associated Article cannot be null in Image");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new ImageDTO(
                image.getId(),
                image.getArticle().getArticleId(), // Ensure the article is not null before calling this
                image.getFilename(),
                image.getMimeType(),
                image.getSize(),
                image.getUploadedAt() != null ? image.getUploadedAt().format(formatter) : null, // Handle potential nulls
                Image.getImageUrl(image.getId())
        );
    }


    /**
     * Maps a Comment entity to a CommentDTO.
     * @param comment the Comment entity.
     * @return the mapped CommentDTO.
     */
    public static CommentDTO mapToCommentDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getArticle().getArticleId(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getContent(),
                comment.getParentComment() != null ? comment.getParentComment().getId() : null,
                comment.getParentComment() != null,
                comment.getCreatedAt().toString()
        );
    }

    /**
     * Maps a CommentDTO to a Comment entity.
     * @param commentDTO the CommentDTO.
     * @param article the associated Article entity.
     * @param user the User entity creating the comment.
     * @param parentComment the parent Comment entity, if any.
     * @return the mapped Comment entity.
     */
    public static Comment mapToCommentEntity(CommentDTO commentDTO, Article article, User user, Comment parentComment) {
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setUser(user);
        comment.setContent(commentDTO.getContent());
        comment.setParentComment(parentComment);
        return comment;
    }


    /**
     * Maps a Version entity to a VersionDTO.
     * @param version the Version entity.
     * @return the mapped VersionDTO.
     */
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

    /**
     * Maps a VersionDTO to a Version entity.
     * @param versionDTO the VersionDTO.
     * @param article the associated Article entity.
     * @param versionNumber the version number.
     * @return the mapped Version entity.
     */
    public static Version mapToVersionEntity(VersionDTO versionDTO, Article article, int versionNumber) {
        Version version = new Version();
        version.setArticle(article);
        version.setContent(versionDTO.getContent());
        version.setVersionNumber(versionNumber);
        return version;
    }
}
