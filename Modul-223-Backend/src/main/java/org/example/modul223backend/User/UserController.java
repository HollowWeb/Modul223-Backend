package org.example.modul223backend.User;

import jakarta.validation.Valid;
import org.example.modul223backend.User.DTO.LoginRequestDTO;
import org.example.modul223backend.User.DTO.UserCreateDTO;
import org.example.modul223backend.User.DTO.UserDTO;
import org.example.modul223backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;
    private final JwtUtil jwtUtil;

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
        UserDTO registeredUser = userService.createUser(userCreateDTO);

        String token = jwtUtil.generateToken(registeredUser.getUsername(), registeredUser.getRoles());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        Map<String, Object> response = new HashMap<>();
        response.put("user", registeredUser);
        response.put("message", "Registration successful");

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
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

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = userService.login(loginRequestDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
