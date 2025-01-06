package org.example.modul223backend.Comment;


import org.example.modul223backend.Article.Article;
import org.example.modul223backend.Article.ArticleRepository;
import org.example.modul223backend.Comment.CommentDTO;
import org.example.modul223backend.Comment.Comment;
import org.example.modul223backend.Comment.CommentRepository;
import org.example.modul223backend.User.User;
import org.example.modul223backend.User.UserRepository;
import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        Article article = articleRepository.findById(commentDTO.getArticleId())
                .orElseThrow(() -> new RuntimeException("Article not found"));

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment parentComment = null;
        if (commentDTO.getParentCommentId() != null) {
            parentComment = commentRepository.findById(commentDTO.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
        }

        Comment comment = Mapper.mapToCommentEntity(commentDTO, article, user, parentComment);
        comment = commentRepository.save(comment);
        return Mapper.mapToCommentDTO(comment);
    }

    @Override
    public List<CommentDTO> getCommentsByArticle(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream().map(Mapper::mapToCommentDTO).collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getReplies(Long parentCommentId) {
        List<Comment> replies = commentRepository.findByParentCommentId(parentCommentId);
        return replies.stream().map(Mapper::mapToCommentDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found");
        }
        commentRepository.deleteById(id);
    }
}

