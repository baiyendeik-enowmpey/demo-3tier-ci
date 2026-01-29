package com.baiyendeik.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baiyendeik.demo.model.User;
import com.baiyendeik.demo.repository.UserRepository;

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
        User user = new User(null, "Test", "test@gmail.com");
        when(userRepository.save(user)).thenReturn(new User(1L, "Test", "test@gmail.com"));
        User saved = userService.createUser(user);
        assertEquals(1L, saved.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById_existingId_returnsUser() {
        User user = new User(1L, "Test", "test@gmail.com");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        java.util.Optional<User> found = userService.getUserById(1L);
        assertEquals(true, found.isPresent());
        assertEquals("Test", found.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getAllUsers_returnsUserList() {
        User user1 = new User(1L, "User1", "user1@gmail.com");
        User user2 = new User(2L, "User2", "user2@gmail.com");
        when(userRepository.findAll()).thenReturn(java.util.Arrays.asList(user1, user2));
        java.util.List<User> allUsers = userService.getAllUsers();
        assertEquals(2, allUsers.size());
        verify(userRepository, times(1)).findAll();
    }
}