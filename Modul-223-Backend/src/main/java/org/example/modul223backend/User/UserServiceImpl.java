package org.example.modul223backend.User;

import jakarta.transaction.Transactional;
import org.example.modul223backend.Article.ArticleRepository;
import org.example.modul223backend.Comment.CommentRepository;
import org.example.modul223backend.Image.ImageRepository;
import org.example.modul223backend.Role.Role;
import org.example.modul223backend.Role.RoleConstants;
import org.example.modul223backend.Role.RoleRepository;
import org.example.modul223backend.User.DTO.LoginRequestDTO;
import org.example.modul223backend.User.DTO.UserCreateDTO;
import org.example.modul223backend.User.DTO.UserDTO;
import org.example.modul223backend.exception.RoleException.RoleNotFoundException;
import org.example.modul223backend.exception.UserException.DuplicateUserException;
import org.example.modul223backend.exception.UserException.UnauthorizedActionException;
import org.example.modul223backend.exception.UserException.UserException;
import org.example.modul223backend.exception.UserException.UserNotFoundException;
import org.example.modul223backend.util.JwtUtil;
import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;

    // Constructor-based Dependency Injection
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil, ArticleRepository articleRepository, CommentRepository commentRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByUsername(userCreateDTO.getUsername())) {
            throw new DuplicateUserException("Username already taken.");
        }
        if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
            throw new DuplicateUserException("Email already registered.");
        }

        Set<Role> roles = getRolesFromNames(Set.of(RoleConstants.USER));

        if (userCreateDTO.getPassword() == null || userCreateDTO.getPassword().isEmpty()){
            throw new UserException("Password cannot be null or empty.", HttpStatus.CONFLICT);
        }

        User user = Mapper.mapToEntity(userCreateDTO, roles);
        user.setPasswordHash(passwordEncoder.encode(userCreateDTO.getPassword()));
        user = userRepository.save(user);
        return Mapper.mapToDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        return Mapper.mapToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(Mapper::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        // Allow updates only by the user themselves or an admin
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user not found."));

        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleConstants.ADMIN));

        if (!isAdmin && !currentUser.getId().equals(id)) {
            throw new UnauthorizedActionException("You are not authorized to update this user.");
        }

        // Update user fields
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Handle roles
        Set<Role> roles = getRolesFromNames(userDTO.getRoles());

        user.setRoles(roles);

        user = userRepository.save(user);
        return Mapper.mapToDTO(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsernameAndDeletedFalse(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user not found."));
        User userToDelete = userRepository.findActiveById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleConstants.ADMIN));

        if (!isAdmin && !currentUser.getId().equals(id)) {
            throw new UnauthorizedActionException("Only admins can delete other users' accounts.");
        }

        // Soft delete user and related data
        markAsDeleted(userToDelete);
    }

    private void markAsDeleted(User user) {
        user.setDeleted(true);

        // Soft delete related entities
        articleRepository.softDeleteByUser(user);  // Mark user's articles as deleted
        commentRepository.softDeleteByUser(user); // Mark user's comments as deleted
        imageRepository.softDeleteByUser(user.getId());   // Mark user's images as deleted

        // Save the user to persist the `deleted` flag
        userRepository.save(user);
    }



    @Override
    public String login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password."));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedActionException("Invalid username or password.");
        }

        // Include roles in the token payload for frontend use
        Set<String> roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());
        return jwtUtil.generateToken(user.getUsername(), roles);
    }

    private Set<Role> getRolesFromNames(Set<String> roleNames) {
        return roleNames.stream()
                .map(roleName -> roleRepository.findByRoleName(roleName)
                        .orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName)))
                .collect(Collectors.toSet());
    }

    private boolean isAdminOrOwner(User currentUser, User targetUser) {
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleConstants.ADMIN));
        return isAdmin || currentUser.getId().equals(targetUser.getId());
    }

}

