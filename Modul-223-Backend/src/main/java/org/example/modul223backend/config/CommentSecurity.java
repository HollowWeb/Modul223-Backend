package org.example.modul223backend.config;

import org.example.modul223backend.Comment.CommentRepository;
import org.example.modul223backend.User.User;
import org.example.modul223backend.User.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("commentSecurity")
public class CommentSecurity {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentSecurity(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public boolean isOwner(Authentication authentication, Long commentId) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return commentRepository.findById(commentId)
                .map(comment -> comment.getUser().getId().equals(user.getId()))
                .orElse(false);
    }
}

