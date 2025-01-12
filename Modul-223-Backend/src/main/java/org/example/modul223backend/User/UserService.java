package org.example.modul223backend.User;


import org.example.modul223backend.User.DTO.LoginRequestDTO;
import org.example.modul223backend.User.DTO.UserCreateDTO;
import org.example.modul223backend.User.DTO.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserCreateDTO userCreateDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    String login(LoginRequestDTO loginRequestDTO);
}
