package com.example.testtask.service;

import com.example.testtask.entity.User;
import com.example.testtask.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository1;
    @Mock
    private UserRepository userRepository2;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        List<UserRepository> dependencies = new ArrayList<>();
        dependencies.add(userRepository1);
        dependencies.add(userRepository2);

        userService.setUserRepositories(dependencies);
    }

    @Test
    void getAllUsers_ReturnsUsers() {
        List<User> mockUsers = List.of(
                new User(1L, "john_doe", "John", "Doe"),
                new User(2L, "anna_smith", "Anna", "Smith")
        );

        when(userRepository1.findAll()).thenReturn(mockUsers);
        when(userRepository2.findAll()).thenReturn(mockUsers);

        List<User> result = userService.getAllUsers();

        assertEquals(4, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
        assertEquals("anna_smith", result.get(1).getUsername());
        verify(userRepository1, times(1)).findAll();
        verify(userRepository2, times(1)).findAll();
    }
}