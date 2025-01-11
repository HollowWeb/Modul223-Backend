package org.example.modul223backend.User;


import java.util.List;

public interface UserService {
    UserDTO createUser(UserCreateDTO userCreateDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    String login(LoginRequestDTO loginRequestDTO);
}
