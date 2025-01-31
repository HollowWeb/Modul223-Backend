package org.example.modul223backend;

import org.example.modul223backend.User.DTO.*;
import org.example.modul223backend.User.UserController;
import org.example.modul223backend.User.UserService;
import org.example.modul223backend.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_ShouldReturnCreatedUserAndToken() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("testUser", "test@example.com", "password123");
        UserDTO registeredUser = new UserDTO(1L, "testUser", "test@example.com", Set.of("USER"));
        when(userService.createUser(userCreateDTO)).thenReturn(registeredUser);
        when(jwtUtil.generateToken(anyString(), anyLong(), anySet())).thenReturn("mockedToken");

        ResponseEntity<Object> response = userController.registerUser(userCreateDTO);

        assertEquals(201, response.getStatusCodeValue());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals("Registration and login successful", responseBody.get("message"));
        verify(userService, times(1)).createUser(userCreateDTO);
    }

    @Test
    void getUserById_ShouldReturnUser() {
        UserDTO mockUser = new UserDTO(1L, "testUser", "test@example.com", Set.of("USER"));
        when(userService.getUserById(1L)).thenReturn(mockUser);

        UserDTO response = userController.getUserById(1L);

        assertNotNull(response);
        assertEquals("testUser", response.getUsername());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void getAllUsers_ShouldReturnUserList() {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        List<UserDTO> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(0, response.size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        UserDTO updateDTO = new UserDTO(1L, "updatedUser", "updated@example.com", Set.of("USER"));
        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(updateDTO);

        UserDTO response = userController.updateUser(1L, updateDTO);

        assertNotNull(response);
        assertEquals("updatedUser", response.getUsername());
        verify(userService, times(1)).updateUser(1L, updateDTO);
    }

    @Test
    void deleteUser_ShouldReturnSuccessMessage() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<String> response = userController.deleteUser(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User deleted successfully.", response.getBody());
        verify(userService, times(1)).deleteUser(1L);
    }
}
