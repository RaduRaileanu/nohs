package ro.bynaus.nohs.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ro.bynaus.nohs.models.SubscriptionDTO;
import ro.bynaus.nohs.security.UserPrincipal;
import ro.bynaus.nohs.services.SubscriptionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionControllerTest {

    @InjectMocks
    private SubscriptionController subscriptionController;

    @Mock
    private SubscriptionService subscriptionService;

    @Test
    public void testGetAuthUserSubscription() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        SubscriptionDTO mockedSubscription = new SubscriptionDTO();
        when(subscriptionService.getAuthUserSubscription(any())).thenReturn(mockedSubscription);

        // Perform the test
        SubscriptionDTO response = subscriptionController.getAuthUserSubscription(principal);

        // Verify the results
        assertNotNull(response);
        assertEquals(mockedSubscription, response);
    }

    @Test
    public void testCreateSubscription() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        Integer serviceId = 1;
        SubscriptionDTO mockedSubscription = new SubscriptionDTO();
        when(subscriptionService.createSubscription(any(), anyInt())).thenReturn(mockedSubscription);

        // Perform the test
        ResponseEntity<SubscriptionDTO> response = subscriptionController.createSubscription(principal, serviceId);

        // Verify the results
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockedSubscription, response.getBody());
    }

    @Test
    public void testCreateSubscription_Forbidden() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        Integer serviceId = 1; 
        when(subscriptionService.createSubscription(any(), anyInt())).thenThrow(new SomeForbiddenException());

        // Perform the test
        ResponseEntity<SubscriptionDTO> response = subscriptionController.createSubscription(principal, serviceId);

        // Verify the results
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateSubscription() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        Integer serviceId = 1; 
        SubscriptionDTO mockedSubscription = new SubscriptionDTO();
        when(subscriptionService.updateSubscription(any(), anyInt())).thenReturn(mockedSubscription);

        // Perform the test
        ResponseEntity<SubscriptionDTO> response = subscriptionController.updateSubscription(principal, serviceId);

        // Verify the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockedSubscription, response.getBody());
    }

    @Test
    public void testUpdateSubscription_Forbidden() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        Integer serviceId = 1; 
        when(subscriptionService.updateSubscription(any(), anyInt())).thenThrow(new SomeForbiddenException());

        // Perform the test
        ResponseEntity<SubscriptionDTO> response = subscriptionController.updateSubscription(principal, serviceId);

        // Verify the results
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
    }

    private static class SomeForbiddenException extends RuntimeException {
    }
}