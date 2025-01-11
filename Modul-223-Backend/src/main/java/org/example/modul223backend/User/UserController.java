package org.example.modul223backend.User;

import jakarta.validation.Valid;
import org.example.modul223backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        UserDTO registeredUser = userService.createUser(userCreateDTO);
        // TODO: IMPLEMENT RETURNING OF TOKEN ON USER REGISTER
        //String token = jwtUtil.generateToken(registeredUser.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("user", registeredUser);
        //response.put("token", token);
        response.put("message", "Registration successful");

        return ResponseEntity.ok(response);
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

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = userService.login(loginRequestDTO);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "Login successful");
        return ResponseEntity.ok(response);
    }
}
