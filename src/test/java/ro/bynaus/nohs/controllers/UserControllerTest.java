package ro.bynaus.nohs.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import ro.bynaus.nohs.models.UserDTO;
import ro.bynaus.nohs.security.UserPrincipal;
import ro.bynaus.nohs.services.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    // @Test
    // public void testUpdateUser() {
    //     // Mock data
    //     UserPrincipal principal = mock(UserPrincipal.class);
    //     UserDTO userDTO = new UserDTO();
    //     UserDTO updatedUserDTO = new UserDTO();
    //     when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    //     when(userService.updateUser(any(), any())).thenReturn(updatedUserDTO);

    //     // Perform the test
    //     UserDTO response = userController.updateUser(principal, userDTO);

    //     // Verify the results
    //     assertNotNull(response);
    //     assertEquals(updatedUserDTO, response);
    //     verify(passwordEncoder, times(1)).encode(userDTO.getPassword());
    // }

    @Test
    public void testSoftDeleteAccount() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);

        // Perform the test
        ResponseEntity<Void> response = userController.softDeleteAccount(principal);

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).softDeleteUser(principal);
    }

    @Test
    public void testSoftDeleteEmployee() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        Integer userId = 1;

        // Perform the test
        ResponseEntity<Void> response = userController.softDeleteEmployee(principal, userId);

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).softDeleteEmployee(principal, userId);
    }
}