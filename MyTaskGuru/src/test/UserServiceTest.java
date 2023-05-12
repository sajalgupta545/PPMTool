package com.example.mytaskguru.services;

import com.example.mytaskguru.domain.User;
import com.example.mytaskguru.exceptions.UsernameAlreadyExistsException;
import com.example.mytaskguru.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_UniqueUsername_UserSaved() {
        // Arrange
        User newUser = new User();
        newUser.setUsername("john");
        newUser.setPassword("password");
        newUser.setConfirmPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(bCryptPasswordEncoder.encode(newUser.getPassword())).thenReturn("hashedPassword");

        // Act
        User savedUser = userService.saveUser(newUser);

        // Assert
        assertNotNull(savedUser);
        assertEquals(newUser.getUsername(), savedUser.getUsername());
        assertEquals("hashedPassword", savedUser.getPassword());
        assertEquals("", savedUser.getConfirmPassword());

        verify(userRepository, times(1)).save(newUser);
        verify(bCryptPasswordEncoder, times(1)).encode(newUser.getPassword());
    }

    @Test
    void saveUser_DuplicateUsername_ThrowException() {
        // Arrange
        User existingUser = new User();
        existingUser.setUsername("john");

        User newUser = new User();
        newUser.setUsername("john");
        newUser.setPassword("password");
        newUser.setConfirmPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act and Assert
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.saveUser(newUser));

        verify(userRepository, times(1)).save(newUser);
    }
}
