package org.example.modul223backend.User;

import org.example.modul223backend.User.UserDTO;
import org.example.modul223backend.User.User;
import org.example.modul223backend.Role.Role;
import org.example.modul223backend.User.UserRepository;
import org.example.modul223backend.Role.RoleRepository;
import org.example.modul223backend.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username already taken.");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }

        Role role = roleRepository.findByRoleName(userDTO.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = Mapper.mapToEntity(userDTO, role);
        user.setPasswordHash(passwordEncoder.encode("password123")); // Default password
        user = userRepository.save(user);
        return Mapper.mapToDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));
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
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        Role role = roleRepository.findByRoleName(userDTO.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        user = userRepository.save(user);
        return Mapper.mapToDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found.");
        }
        userRepository.deleteById(id);
    }
}

