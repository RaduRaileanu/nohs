package ro.bynaus.nohs.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.UserMapper;
import ro.bynaus.nohs.models.LoginCredentialsDTO;
import ro.bynaus.nohs.models.LoginResponse;
import ro.bynaus.nohs.models.RegisterResponse;
import ro.bynaus.nohs.models.UserDTO;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.JwtIssuer;
import ro.bynaus.nohs.security.UserPrincipal;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private JwtIssuer jwtIssuer;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testLogin() {
        // Mock data
        LoginCredentialsDTO loginCredentials = new LoginCredentialsDTO("test@example.com", "password");
        Authentication authentication = mock(Authentication.class);
        UserPrincipal principal = mock(UserPrincipal.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(principal.getUserId()).thenReturn(1);
        when(principal.getEmail()).thenReturn("test@example.com");
        when(jwtIssuer.issue(anyInt(), anyString(), anyList())).thenReturn("mocked-token");

        // Perform the test
        LoginResponse response = authController.login(loginCredentials);

        // Verify the results
        assertNotNull(response);
        assertEquals("mocked-token", response.getAccessToken());
    }

    @Test
    public void testCreateUser() {
        // Mock data
        UserDTO userDTO = UserDTO.builder()
                                    .id(1)
                                    .email("test@example.com")
                                    .password("encodedPassword")
                                    .role("admin")
                                    .build();
        User user = new User();
        UserPrincipal principal = UserPrincipal.builder()
                                                .userId(1)
                                                .email("test@example.com")
                                                .password("encodedPassword")
                                                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                                                .build();
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userMapper.userDtoToUser(userDTO)).thenReturn(user);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(userMapper.userToUserDto(user)).thenReturn(userDTO);
        when(jwtIssuer.issue(anyInt(), anyString(), anyList())).thenReturn("mocked-token");

        // Perform the test
        RegisterResponse response = authController.createUser(userDTO);

        // Verify the results
        assertNotNull(response);
        assertEquals("mocked-token", response.getAccessToken());
        assertTrue(response.getSuccessfulCompletion());
    }

    @Test
    public void testCreateUser_UserExists() {
        // Mock data
        UserDTO userDTO = UserDTO.builder()
                                    .email("test@example.com")
                                    .password("password")
                                    .role("admin")
                                    .build();
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Perform the test
        RegisterResponse response = authController.createUser(userDTO);

        // Verify the results
        assertNotNull(response);
        assertFalse(response.getSuccessfulCompletion());
        assertEquals("A user with the same email is already registered", response.getMessage());
        assertEquals("", response.getAccessToken());
    }
}