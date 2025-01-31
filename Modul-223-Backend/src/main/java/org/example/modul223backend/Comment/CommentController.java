package org.example.modul223backend.Comment;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {


    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentDTO addComment(@RequestBody @Valid CommentDTO commentDTO) {
        return commentService.addComment(commentDTO);
    }

    @GetMapping("/article/{articleId}")
    public List<CommentDTO> getCommentsByArticle(@PathVariable Long articleId) {
        return commentService.getCommentsByArticle(articleId);
    }

    @GetMapping("/replies/{parentCommentId}")
    public List<CommentDTO> getReplies(@PathVariable Long parentCommentId) {
        return commentService.getReplies(parentCommentId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @commentSecurity.isOwner(authentication, #id)")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}


