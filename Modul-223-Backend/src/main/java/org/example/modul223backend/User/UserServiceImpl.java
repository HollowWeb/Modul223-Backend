package org.example.modul223backend.User;

import org.example.modul223backend.User.UserDTO;
import org.example.modul223backend.User.User;
import org.example.modul223backend.Role.Role;
import org.example.modul223backend.User.UserRepository;
import org.example.modul223backend.Role.RoleRepository;
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

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Constructor-based Dependency Injection
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByUsername(userCreateDTO.getUsername())) {
            throw new DuplicateUserException("Username already taken.");
        }
        if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
            throw new DuplicateUserException("Email already registered.");
        }

        Set<Role> roles = Set.of(roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RoleNotFoundException("Default role 'USER' not found.")));

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
                .anyMatch(role -> role.getRoleName().equals("ADMIN"));

        if (!isAdmin && !currentUser.getId().equals(id)) {
            throw new UnauthorizedActionException("You are not authorized to update this user.");
        }

        // Update user fields
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Handle roles
        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleName -> roleRepository.findByRoleName(roleName)
                        .orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName)))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        user = userRepository.save(user);
        return Mapper.mapToDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        // Get the current authenticated user's details
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("Current user not found."));

        // Allow deletion if the current user is an admin or deleting their own account
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals("ADMIN"));

        if (!isAdmin && !currentUser.getId().equals(id)) {
            throw new UnauthorizedActionException("You are not authorized to delete this user.");
        }

        // Proceed with deletion if the user exists
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        userRepository.delete(userToDelete);
    }

    @Override
    public String login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password."));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedActionException("Invalid username or password.");
        }

        // Include roles in the token payload for frontend use
        String roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.joining(","));
        return jwtUtil.generateToken(user.getUsername(), roles);
    }

}

