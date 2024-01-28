package ro.bynaus.nohs.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.OrganisationMapper;
import ro.bynaus.nohs.models.OrganisationDTO;
import ro.bynaus.nohs.repositories.OrganisationRepository;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrganisationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganisationRepository organisationRepository;

    @Mock
    private OrganisationMapper organisationMapper;

    @InjectMocks
    private OrganisationServiceImpl organisationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAuthUserOrganisation() {
        // Mock data
        UserPrincipal principal = UserPrincipal.builder()
                                                .userId(1)
                                                .email("user@example.com")
                                                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                                                .build();
        User user = new User();
        Organisation organisation = new Organisation();
        organisation.setName("Test Org");
        user.setOrganisation(organisation);
        OrganisationDTO organisationDTO = OrganisationDTO.builder()
                                                            .name("Test Org")
                                                            .build();

        // Mock UserRepository call
        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        // Mock OrganisationMapper call
        when(organisationMapper.organisationToOrganisationDTO(organisation)).thenReturn(organisationDTO);

        // Perform the test
        OrganisationDTO result = organisationService.getAuthUserOrganisation(principal);

        // Verify the results
        assertNotNull(result);
        assertEquals("Test Org", result.getName());
    }

    @Test
    public void testStoreOrganisation() {
        // Mock data
        UserPrincipal principal = UserPrincipal.builder()
                                                .userId(1)
                                                .email("user@example.com")
                                                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                                                .build();
        OrganisationDTO organisationDto = new OrganisationDTO();
        organisationDto.setName("New Org");
        User user = new User();
        Organisation organisation = new Organisation();
        organisation.setName("New Org");

        // Mock UserRepository call
        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        // Mock OrganisationMapper calls
        when(organisationMapper.organisationDTOToOrganisation(organisationDto)).thenReturn(organisation);
        when(organisationMapper.organisationToOrganisationDTO(organisation)).thenReturn(organisationDto);
        // Mock OrganisationRepository call
        when(organisationRepository.saveAndFlush(organisation)).thenReturn(organisation);

        // Perform the test
        OrganisationDTO result = organisationService.storeOrganisation(principal, organisationDto);

        // Verify the results
        assertNotNull(result);
        assertEquals("New Org", result.getName());
    }

    @Test
    public void testUpdateOrganisation() {
        // Mock data
        UserPrincipal principal = UserPrincipal.builder()
                                                .userId(1)
                                                .email("user@example.com")
                                                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                                                .build();
        OrganisationDTO organisationDto = new OrganisationDTO();
        organisationDto.setName("Updated Org");
        User user = new User();
        user.setRole("admin");
        Organisation existingOrganisation = new Organisation();
        existingOrganisation.setName("Old Org");
        OrganisationDTO existingOrganisationDTO = new OrganisationDTO();
        existingOrganisationDTO.setName("Old Org");
        user.setOrganisation(existingOrganisation);

        // Mock UserRepository call
        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        // Mock OrganisationMapper calls
        when(organisationMapper.organisationToOrganisationDTO(existingOrganisation)).thenReturn(existingOrganisationDTO);
        when(organisationMapper.organisationDTOToOrganisation(existingOrganisationDTO)).thenReturn(existingOrganisation);
        // Mock OrganisationRepository call
        when(organisationRepository.saveAndFlush(existingOrganisation)).thenReturn(existingOrganisation);

        // Perform the test
        OrganisationDTO result = organisationService.updateOrganisation(principal, organisationDto);

        // Verify the results
        assertNotNull(result);
        assertEquals("Updated Org", result.getName());
    }

    @Test
    public void testSoftDeleteOrganisation() {
        // Mock data
        UserPrincipal principal = UserPrincipal.builder()
                                                .userId(1)
                                                .email("user@example.com")
                                                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                                                .build();
        User user = new User();
        user.setRole("admin");
        Organisation organisation = new Organisation();
        user.setOrganisation(organisation);

        // Mock UserRepository call
        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        // Mock OrganisationRepository call
        when(organisationRepository.save(organisation)).thenReturn(organisation);

        // Perform the test
        assertDoesNotThrow(() -> organisationService.softDeleteOrganisation(principal));
    }
}