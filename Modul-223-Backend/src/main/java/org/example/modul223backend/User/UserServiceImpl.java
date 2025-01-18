package org.example.modul223backend.User;

import jakarta.transaction.Transactional;
import org.example.modul223backend.Article.ArticleRepository;
import org.example.modul223backend.Comment.CommentRepository;
import org.example.modul223backend.Image.ImageRepository;
import org.example.modul223backend.Role.Role;
import org.example.modul223backend.Role.RoleConstants;
import org.example.modul223backend.Role.RoleRepository;
import org.example.modul223backend.User.DTO.*;
import org.example.modul223backend.exception.ErrorMessages;
import org.example.modul223backend.exception.RoleException.RoleNotFoundException;
import org.example.modul223backend.exception.UserException.DuplicateUserException;
import org.example.modul223backend.exception.UserException.UnauthorizedActionException;
import org.example.modul223backend.exception.UserException.UserException;
import org.example.modul223backend.exception.UserException.UserNotFoundException;
import org.example.modul223backend.util.JwtUtil;
import org.example.modul223backend.util.Mapper;
import org.example.modul223backend.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
            throw new DuplicateUserException(ErrorMessages.DUPLICATE_EMAIL);
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
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));
        return Mapper.mapToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(Mapper::mapToDTO).toList();
    }

    @Transactional
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        // Allow updates only by the user themselves or an admin
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user not found."));

        boolean isAdmin = UserUtils.hasRole(currentUser, RoleConstants.ADMIN);

        // only a admin or the current user can delete the account
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
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        boolean isAdmin = UserUtils.hasRole(currentUser, RoleConstants.ADMIN);

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

    @Transactional
    @Override
    public UserDTO updateUserSetting(UserUpdateDTO updateDTO) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsernameAndDeletedFalse(currentUsername)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        if (updateDTO.getUsername() != null) {
            user.setUsername(updateDTO.getUsername());
        }
        if (updateDTO.getEmail() != null) {
            if (userRepository.existsByEmail(updateDTO.getEmail())) {
                throw new DuplicateUserException(ErrorMessages.DUPLICATE_EMAIL);
            }
            user.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(updateDTO.getPassword()));
        }

        user = userRepository.save(user);
        return Mapper.mapToDTO(user);
    }

    @Transactional
    @Override
    public UserDTO adminUpdateUser(Long id, UserAdminUpdateDTO updateDTO) {

        User user = userRepository.findActiveById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        if (updateDTO.getUsername() != null) {
            user.setUsername(updateDTO.getUsername());
        }

        if (updateDTO.getEmail() != null) {
            if (userRepository.existsByEmail(updateDTO.getEmail())) {
                throw new DuplicateUserException(ErrorMessages.DUPLICATE_EMAIL);
            }
            user.setEmail(updateDTO.getEmail());
        }

        if (updateDTO.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(updateDTO.getPassword()));
        }

        if (updateDTO.getRoles() != null) {
            Set<Role> roles = getRolesFromNames(updateDTO.getRoles());
            user.setRoles(roles);
        }

        user = userRepository.save(user);
        return Mapper.mapToDTO(user);
    }


    private Set<Role> getRolesFromNames(Set<String> roleNames) {
        return roleNames.stream()
                .map(roleName -> roleRepository.findByRoleName(roleName)
                        .orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName)))
                .collect(Collectors.toSet());
    }

    @Transactional
    public void reactivateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("Could not find user with id = " + id, HttpStatus.BAD_REQUEST));
        user.setDeleted(false);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void changePassword(Long id, PasswordChangeDTO passwordChangeDTO) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsernameAndDeletedFalse(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user not found."));
        User userToUpdate = userRepository.findActiveById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));

        // Only allow the user themselves or an admin to update the password
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleConstants.ADMIN));
        if (!isAdmin && !currentUser.getId().equals(id)) {
            throw new UnauthorizedActionException("You are not authorized to change this password.");
        }

        // Verify old password if the user is updating their own password
        if (!isAdmin && !passwordEncoder.matches(passwordChangeDTO.getOldPassword(), userToUpdate.getPasswordHash())) {
            throw new UserException("Incorrect current password.", HttpStatus.BAD_REQUEST);
        }

        // Update the password
        userToUpdate.setPasswordHash(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        userRepository.save(userToUpdate);
    }

    @Override
    public List<UserDTO> searchUsers(String username, String email, String role) {
        List<User> users = userRepository.searchUsers(username, email, role);
        return users.stream()
                .map(Mapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsernameAndDeletedFalse(currentUsername)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));
        return Mapper.mapToDTO(currentUser);
    }


}

