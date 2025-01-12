package org.example.modul223backend.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public CommentDTO addComment(@RequestBody CommentDTO commentDTO) {
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
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}

