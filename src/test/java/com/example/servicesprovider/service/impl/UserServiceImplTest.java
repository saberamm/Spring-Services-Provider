package com.example.servicesprovider.service.impl;

import com.example.servicesprovider.exception.PasswordsNotEqualException;
import com.example.servicesprovider.exception.UsernameOrPasswordNotCorrectException;
import com.example.servicesprovider.model.User;
import com.example.servicesprovider.repository.UserRepository;
import com.example.servicesprovider.service.UserService;
import com.example.servicesprovider.utility.HashGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HashGenerator hashGenerator;

    @Mock
    private Validator validator;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository, validator, hashGenerator);
    }


    @Test
    public void testFindByUserName() {
        String username = "testuser";
        User user = new User();
        user.setUserName(username);

        when(userRepository.findByUserName(username)).thenReturn(user);

        User retrievedUser = userService.findByUserName(username);

        assertEquals(user, retrievedUser);
    }

    @Test
    public void testChangePassword() {
        String userName = "testuser";
        String currentPassword = "oldpassword";
        String newPassword = "newpassword";
        String duplicateNewPassword = "newpassword";

        User user = new User();
        user.setUserName(userName);
        user.setPassword(hashGenerator.generateSHA512Hash(currentPassword));

        when(userRepository.findByUserNameAndPassword(userName, user.getPassword())).thenReturn(user);

        userService.changePassword(userName, currentPassword, newPassword, duplicateNewPassword);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testChangePassword_PasswordsNotEqualException() {
        String userName = "testuser";
        String currentPassword = "oldpassword";
        String newPassword = "newpassword";
        String duplicateNewPassword = "differentpassword";

        User user = new User();
        user.setUserName(userName);
        user.setPassword(hashGenerator.generateSHA512Hash(currentPassword));

        when(userRepository.findByUserNameAndPassword(userName, user.getPassword())).thenReturn(user);

        try {
            userService.changePassword(userName, currentPassword, newPassword, duplicateNewPassword);
        } catch (Exception ignored) {
        }

        verify(userRepository, never()).save(user);
    }

    @Test
    public void testUserAuthentication() {
        String userName = "testuser";
        String password = "password";

        User user = new User();
        user.setUserName(userName);
        user.setPassword(hashGenerator.generateSHA512Hash(password));

        when(userRepository.findByUserNameAndPassword(userName, user.getPassword())).thenReturn(user);

        User authenticatedUser = userService.userAuthentication(userName, password);

        assertEquals(user, authenticatedUser);
    }

    @Test
    public void testUserAuthentication_UsernameOrPasswordNotCorrectException() {
        String userName = "testuser";
        String password = "wrongpassword";

        when(userRepository.findByUserNameAndPassword(userName, hashGenerator.generateSHA512Hash(password))).thenReturn(null);

        userService.userAuthentication(userName, password);

        verify(userRepository, never()).save(any());
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("password");

        when(hashGenerator.generateSHA512Hash(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        assertEquals(user, savedUser);
    }

    @Test
    public void testSave_Error() {
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("password");

        when(hashGenerator.generateSHA512Hash(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenThrow(new RuntimeException("Simulated save error"));

        User savedUser = userService.save(user);

        assertNull(savedUser);
    }
}
