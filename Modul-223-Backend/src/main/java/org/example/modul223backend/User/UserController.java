package org.example.modul223backend.User;

import jakarta.validation.Valid;
import org.example.modul223backend.Role.RoleConstants;
import org.example.modul223backend.User.DTO.*;
import org.example.modul223backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serves as a Controller for reaching out to different ENDPOINTS,
 * triggering the specific method in it.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Initializes a new instance of <see="">UserController</see="UserController"> class.
     * Including the specific dependency injection classes that implements the logic.
     * */
    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * EndPoint /api/users/register
     * Registers a new user and gives him the Default Role USER
     * @param userCreateDTO UserName, Valid Email and Password.
     * @return Registered User with USER Role and Bearer Token in Header.
     */
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        // Create the user
        UserDTO registeredUser = userService.createUser(userCreateDTO);

        // Generate JWT token
        String token = jwtUtil.generateToken(registeredUser.getUsername(), registeredUser.getRoles());

        // Set Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Prepare response body
        Map<String, Object> response = new HashMap<>();
        response.put("user", registeredUser);
        response.put("message", "Registration and login successful");

        //Just for debugging purposes nibba.
        System.out.println("Generated JWT Token: Bearer " + token);


        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    /**
     * Deleting a user can only happen by the User himself or a User with the Role ADMIN
     * @param id ID of user to delete
     * @return Success Body
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    /**
     * Login Endpoint including
     * the method call for the login process logic.
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = userService.login(loginRequestDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @PutMapping("/settings")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO updatedUser = userService.updateUserSetting(userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}/admin-update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> adminUpdateUser(@PathVariable Long id, @RequestBody UserAdminUpdateDTO userUpdateDTO) {
        UserDTO updatedUser = userService.adminUpdateUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{id}/reactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> reactivateUser(@PathVariable Long id) {
        userService.reactivateUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User reactivated successfully.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        userService.changePassword(id, passwordChangeDTO);
        return ResponseEntity.ok("Password updated successfully.");
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> searchUsers(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "role", required = false) String role) {
        return userService.searchUsers(username, email, role);
    }

    @GetMapping("/me")
    public UserDTO getCurrentUser() {
        return userService.getCurrentUser();
    }

}
