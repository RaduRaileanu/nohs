package ro.bynaus.nohs.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.entities.Subscription;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.SubscriptionMapper;
import ro.bynaus.nohs.models.SubscriptionDTO;
import ro.bynaus.nohs.repositories.OrganisationRepository;
import ro.bynaus.nohs.repositories.ServiceRepository;
import ro.bynaus.nohs.repositories.SubscriptionRepository;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

public class SubscriptionServiceImplTest {

    private SubscriptionServiceImpl subscriptionService;
    private UserRepository userRepository;
    private SubscriptionRepository subscriptionRepository;
    private SubscriptionMapper subscriptionMapper;
    private ServiceRepository serviceRepository;
    private OrganisationRepository organisationRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        subscriptionRepository = mock(SubscriptionRepository.class);
        subscriptionMapper = mock(SubscriptionMapper.class);
        serviceRepository = mock(ServiceRepository.class);
        organisationRepository = mock(OrganisationRepository.class);
        subscriptionService = new SubscriptionServiceImpl(userRepository, subscriptionRepository, subscriptionMapper, serviceRepository, organisationRepository);
    }

    @Test
    public void testGetAuthUserSubscriptionUser() {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("user@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setRole("admin");
        Subscription subscription = new Subscription();
        subscription.setId(1);
        subscription.setBallance(50.0);
        subscription.setTrialRequests(5);
        user.setSubscription(subscription);
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                                                            .id(1)
                                                            .ballance(50.0)
                                                            .trialRequests(5)
                                                            .build();

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        when(subscriptionMapper.subcriptionToSubscriptionDto(subscription)).thenReturn(subscriptionDTO);

        SubscriptionDTO result = subscriptionService.getAuthUserSubscription(principal);

        assertNotNull(result);
        assertEquals(50.0, result.getBallance());
        assertEquals(5, result.getTrialRequests());
    }

    @Test
    public void testGetAuthUserSubscriptionOrganisation() {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("user@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        Organisation organisation = new Organisation();
        organisation.setId(1);
        Subscription subscription = new Subscription();
        subscription.setId(2);
        subscription.setBallance(30.0);
        subscription.setTrialRequests(10);
        organisation.setSubscription(subscription);
        user.setOrganisation(organisation);
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                                                            .id(1)
                                                            .ballance(30.0)
                                                            .trialRequests(10)
                                                            .build();

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        when(subscriptionMapper.subcriptionToSubscriptionDto(subscription)).thenReturn(subscriptionDTO);

        SubscriptionDTO result = subscriptionService.getAuthUserSubscription(principal);

        assertNotNull(result);
        assertEquals(30.0, result.getBallance());
        assertEquals(10, result.getTrialRequests());
    }

    @Test
    public void testCreateSubscriptionForUser() throws Error {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("user@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        Subscription subscription = new Subscription();
        subscription.setId(1);
        subscription.setBallance(0.0);
        subscription.setTrialRequests(50);
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                                                            .id(1)
                                                            .ballance(0.00)
                                                            .trialRequests(50)
                                                            .build();
        Service service = new Service();
        service.setId(1);
        user.setSubscription(subscription);

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        when(serviceRepository.findById(1)).thenReturn(Optional.of(service));
        when(subscriptionRepository.saveAndFlush(any())).thenReturn(subscription);
        when(subscriptionMapper.subcriptionToSubscriptionDto(subscription)).thenReturn(subscriptionDTO);

        SubscriptionDTO result = subscriptionService.createSubscription(principal, 1);

        assertNotNull(result);
        assertEquals(0.0, result.getBallance());
        assertEquals(50, result.getTrialRequests());
    }

    @Test
    public void testCreateSubscriptionForOrganisation() throws Error {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("user@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .build();
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setRole("admin");
        Organisation organisation = new Organisation();
        organisation.setId(1);
        Subscription subscription = new Subscription();
        subscription.setId(2);
        subscription.setBallance(0.0);
        subscription.setTrialRequests(50);
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                                                            .id(1)
                                                            .ballance(0.00)
                                                            .trialRequests(50)
                                                            .build();
        Service service = new Service();
        service.setId(1);
        organisation.setSubscription(subscription);
        user.setOrganisation(organisation);

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        when(serviceRepository.findById(1)).thenReturn(Optional.of(service));
        when(subscriptionRepository.saveAndFlush(any())).thenReturn(subscription);
        when(subscriptionMapper.subcriptionToSubscriptionDto(subscription)).thenReturn(subscriptionDTO);

        SubscriptionDTO result = subscriptionService.createSubscription(principal, 1);

        assertNotNull(result);
        assertEquals(0.0, result.getBallance());
        assertEquals(50, result.getTrialRequests());
    }

    @Test
    public void testUpdateSubscriptionForUser() throws Error {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("user@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setRole("admin");
        Subscription subscription = new Subscription();
        subscription.setId(1);
        subscription.setBallance(20.0);
        subscription.setTrialRequests(30);
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                                                            .id(1)
                                                            .ballance(20.0)
                                                            .trialRequests(30)
                                                            .build();
        
        Service service = new Service();
        service.setId(2);
        user.setSubscription(subscription);

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        when(serviceRepository.findById(2)).thenReturn(Optional.of(service));
        when(subscriptionRepository.saveAndFlush(any())).thenReturn(subscription);
        when(subscriptionMapper.subcriptionToSubscriptionDto(subscription)).thenReturn(subscriptionDTO);

        SubscriptionDTO result = subscriptionService.updateSubscription(principal, 2);

        assertNotNull(result);
        assertEquals(20.0, result.getBallance());
        assertEquals(30, result.getTrialRequests());
    }

    @Test
    public void testUpdateSubscriptionForOrganisation() throws Error {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("user@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .build();
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setRole("admin");
        Organisation organisation = new Organisation();
        organisation.setId(1);
        Subscription subscription = new Subscription();
        subscription.setId(2);
        subscription.setBallance(25.0);
        subscription.setTrialRequests(40);
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                                                            .id(1)
                                                            .ballance(25.0)
                                                            .trialRequests(40)
                                                            .build();
        Service service = new Service();
        service.setId(3);
        organisation.setSubscription(subscription);
        user.setOrganisation(organisation);

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        when(serviceRepository.findById(3)).thenReturn(Optional.of(service));
        when(subscriptionRepository.saveAndFlush(any())).thenReturn(subscription);
        when(subscriptionMapper.subcriptionToSubscriptionDto(subscription)).thenReturn(subscriptionDTO);

        SubscriptionDTO result = subscriptionService.updateSubscription(principal, 3);

        assertNotNull(result);
        assertEquals(25.0, result.getBallance());
        assertEquals(40, result.getTrialRequests());
    }
}
