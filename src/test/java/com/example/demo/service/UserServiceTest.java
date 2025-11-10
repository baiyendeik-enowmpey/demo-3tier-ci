package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void createUser_savesEntity() {
        User user = new User(null, "Test", "test@example.com");
        when(userRepository.save(user)).thenReturn(new User(1L, "Test", "test@example.com"));
        User saved = userService.createUser(user);
        assertEquals(1L, saved.getId());
        verify(userRepository, times(1)).save(user);
    }
}
