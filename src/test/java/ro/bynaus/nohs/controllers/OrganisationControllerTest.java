package ro.bynaus.nohs.controllers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ro.bynaus.nohs.models.OrganisationDTO;
import ro.bynaus.nohs.security.UserPrincipal;
import ro.bynaus.nohs.services.OrganisationService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrganisationControllerTest {

    @InjectMocks
    private OrganisationController organisationController;

    @Mock
    private OrganisationService organisationService;

    @Test
    public void testGetAuthUserOrganisation() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        OrganisationDTO organisationDTO = new OrganisationDTO();
        when(organisationService.getAuthUserOrganisation(any())).thenReturn(organisationDTO);

        // Perform the test
        ResponseEntity<OrganisationDTO> response = organisationController.getAuthUserOrganisation(principal);

        // Verify the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(organisationDTO, response.getBody());
    }

    @Test
    public void testGetAuthUserOrganisation_NotFound() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        when(organisationService.getAuthUserOrganisation(any())).thenReturn(null);

        // Perform the test
        ResponseEntity<OrganisationDTO> response = organisationController.getAuthUserOrganisation(principal);

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testStoreOrganisation() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        OrganisationDTO inputOrganisation = new OrganisationDTO();
        OrganisationDTO savedOrganisation = new OrganisationDTO();
        when(organisationService.storeOrganisation(any(), any())).thenReturn(savedOrganisation);

        // Perform the test
        ResponseEntity<OrganisationDTO> response = organisationController.storeOrganisation(principal, inputOrganisation);

        // Verify the results
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(savedOrganisation, response.getBody());
    }

    @Test
    public void testUpdateOrganisation() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        OrganisationDTO inputOrganisation = new OrganisationDTO();
        OrganisationDTO savedOrganisation = new OrganisationDTO();
        when(organisationService.updateOrganisation(any(), any())).thenReturn(savedOrganisation);

        // Perform the test
        ResponseEntity<OrganisationDTO> response = organisationController.updateOrganisation(principal, inputOrganisation);

        // Verify the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(savedOrganisation, response.getBody());
    }

    @Test
    public void testUpdateOrganisation_NotFound() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        OrganisationDTO inputOrganisation = new OrganisationDTO();
        when(organisationService.updateOrganisation(any(), any())).thenReturn(null);

        // Perform the test
        ResponseEntity<OrganisationDTO> response = organisationController.updateOrganisation(principal, inputOrganisation);

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateOrganisation_Forbidden() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        OrganisationDTO inputOrganisation = new OrganisationDTO();
        when(organisationService.updateOrganisation(any(), any())).thenThrow(new SomeForbiddenException());

        // Perform the test
        ResponseEntity<OrganisationDTO> response = organisationController.updateOrganisation(principal, inputOrganisation);

        // Verify the results
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteOrganisation() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);

        // Perform the test
        ResponseEntity<Void> response = organisationController.deleteOrganisation(principal);

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(organisationService, times(1)).softDeleteOrganisation(principal);
    }

    @Test
    public void testDeleteOrganisation_Forbidden() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        doThrow(new SomeForbiddenException()).when(organisationService).softDeleteOrganisation(principal);

        // Perform the test
        ResponseEntity<Void> response = organisationController.deleteOrganisation(principal);

        // Verify the results
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteOrganisation_BadRequest() {
        // Mock data
        UserPrincipal principal = mock(UserPrincipal.class);
        doThrow(new SomeOtherException()).when(organisationService).softDeleteOrganisation(principal);

        // Perform the test
        ResponseEntity<Void> response = organisationController.deleteOrganisation(principal);

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    private static class SomeForbiddenException extends RuntimeException {
        public SomeForbiddenException() {
            super("The user must be an admin to delete the organisation");
        }
    }

    private static class SomeOtherException extends RuntimeException {
    }
}