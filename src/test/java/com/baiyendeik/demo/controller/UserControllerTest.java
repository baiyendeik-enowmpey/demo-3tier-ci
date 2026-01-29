package com.baiyendeik.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.baiyendeik.demo.dto.UserDTO;
import com.baiyendeik.demo.model.User;
import com.baiyendeik.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    /* ---------------- CREATE USER ---------------- */

    @Test
    void createUser_invalidData_returnsBadRequest() throws Exception {
        UserDTO invalid = new UserDTO();
        invalid.setName("");
        invalid.setEmail("invalid");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name darf nicht leer sein"))
                .andExpect(jsonPath("$.email").value("Ungültige E-Mail-Adresse"));
    }

    @Test
void createUser_validData_returnsCreatedUser() throws Exception {
    UserDTO valid = new UserDTO();
    valid.setName("Valid User");
    valid.setEmail("test@gmail.com");

    when(userService.createUser(any()))
        .thenReturn(new User(1L, "Valid User", "test@gmail.com"));

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(valid)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Valid User"))
        .andExpect(jsonPath("$.email").value("test@gmail.com"));
}


    @Test
    void createUser_missingFields_returnsBadRequest() throws Exception {
        UserDTO missing = new UserDTO();

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(missing)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name darf nicht leer sein"))
                .andExpect(jsonPath("$.email").value("E-Mail darf nicht leer sein"));
    }

    /* ---------------- GET USER BY ID ---------------- */

    @Test
    void getUserById_existingId_returnsUser() throws Exception {
        User user = new User(1L, "Existing User", "test@gmail.com");

        when(userService.getUserById(1L))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Existing User"))
                .andExpect(jsonPath("$.email").value("test@gmail.com"));
    }

    @Test
    void getUserById_nonExistingId_returnsNotFound() throws Exception {
        when(userService.getUserById(999L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/users/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /* ---------------- GET ALL USERS ---------------- */

    @Test
    void getAllUsers_noUsers_returnsEmptyList() throws Exception {
        when(userService.getAllUsers())
                .thenReturn(Arrays.asList());

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getAllUsers_multipleUsers_returnsUserList() throws Exception {
        when(userService.getAllUsers())
                .thenReturn(Arrays.asList(
                        new User(1L, "User One", "user1@gmail.com"),
                        new User(2L, "User Two", "user2@gmail.com")
                ));

        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("User One"))
                .andExpect(jsonPath("$[0].email").value("user1@gmail.com"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("User Two"))
                .andExpect(jsonPath("$[1].email").value("user2@gmail.com"));
    }
}
