package org.example.modul223backend.User;


import org.example.modul223backend.User.DTO.*;

import java.util.List;

/**
 * Service interface for managing user-related operations.
 */
public interface UserService {
    /**
     * Creates a new user with the given user dto data.
     * @param userCreateDTO user dto data.
     * @return a create user dto.
     */
    UserDTO createUser(UserCreateDTO userCreateDTO);

    /**
     * Gets user by given id number.
     * @param id id number of specific user.
     * @return matched user.
     */
    UserDTO getUserById(Long id);

    /**
     * Gets all user
     * @return a list of all users.
     */
    List<UserDTO> getAllUsers();

    /**
     * Updates a user by their ID with the given user data.
     * @param id the ID of the user.
     * @param userDTO updated user data.
     * @return the updated user.
     */
    UserDTO updateUser(Long id, UserDTO userDTO);

    /**
     * Deletes a user by their ID.
     * @param id the ID of the user to delete.
     */
    void deleteUser(Long id);

    /**
     * Authenticates a user and returns a login token.
     * @param loginRequestDTO login credentials.
     * @return a login token.
     */
    String login(LoginRequestDTO loginRequestDTO);

    /**
     * Updates a user's settings.
     * @param updateDTO updated settings data.
     * @return the updated user.
     */
    UserDTO updateUserSetting(UserUpdateDTO updateDTO);

    /**
     * Admin updates a user's data.
     * @param id the ID of the user.
     * @param updateDTO updated data for the user.
     * @return the updated user.
     */
    UserDTO adminUpdateUser(Long id, UserAdminUpdateDTO updateDTO);

    /**
     * Reactivates a user by their ID.
     * @param id the ID of the user to reactivate.
     */
    void reactivateUser(Long id);

    /**
     * Changes the password for a user by their ID.
     * @param id the ID of the user.
     * @param passwordChangeDTO new password data.
     */
    void changePassword(Long id, PasswordChangeDTO passwordChangeDTO);

    /**
     * Searches for users by username, email, or role.
     * @param username the username to search for.
     * @param email the email to search for.
     * @param role the role to search for.
     * @return a list of matched users.
     */
    List<UserDTO> searchUsers(String username, String email, String role);

    /**
     * Gets the currently logged-in user.
     * @return the current user.
     */
    UserDTO getCurrentUser();

}
