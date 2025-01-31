package org.example.modul223backend.User;


import org.example.modul223backend.User.DTO.*;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserCreateDTO userCreateDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    String login(LoginRequestDTO loginRequestDTO);
    UserDTO updateUserSetting(UserUpdateDTO updateDTO);
    UserDTO adminUpdateUser(Long id, UserAdminUpdateDTO updateDTO);
    void reactivateUser(Long id);
    void changePassword(Long id, PasswordChangeDTO passwordChangeDTO);
    List<UserDTO> searchUsers(String username, String email, String role);
    UserDTO getCurrentUser();
    void changePasswordAdmin(Long id, String password);
}
