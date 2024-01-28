package ro.bynaus.nohs.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.UserMapper;
import ro.bynaus.nohs.models.UserDTO;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.UserPrincipal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindByEmail() {
        String userEmail = "test@example.com";
        User user = new User();
        user.setId(1);
        user.setEmail(userEmail);

        when(userRepository.findByEmail(userEmail)).thenReturn(List.of(user));

        Optional<User> result = userService.findByEmail(userEmail);

        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
        assertEquals(user.getEmail(), result.get().getEmail());
    }

    @Test
    void testFindByEmailNotFound() {
        String userEmail = "nonexistent@example.com";

        when(userRepository.findByEmail(userEmail)).thenReturn(List.of());

        Optional<User> result = userService.findByEmail(userEmail);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByEmailDeletedUser() {
        String userEmail = "deleted@example.com";
        User deletedUser = new User();
        deletedUser.setId(1);
        deletedUser.setEmail(userEmail);
        deletedUser.setDeletedAt(LocalDateTime.now());

        when(userRepository.findByEmail(userEmail)).thenReturn(List.of(deletedUser));

        Optional<User> result = userService.findByEmail(userEmail);

        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateUser() {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("test@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");

        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setFirstName("OldFirst");
        existingUser.setLastName("OldLast");

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(existingUser));
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(savedUser);
        when(userMapper.userToUserDto(savedUser)).thenReturn(userDTO);

        UserDTO result = userService.updateUser(principal, userDTO);

        assertNotNull(result);
        assertEquals(userDTO.getFirstName(), result.getFirstName());
        assertEquals(userDTO.getLastName(), result.getLastName());
    }

    @Test
    void testUpdateUserNotFound() {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("test@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.empty());

        UserDTO result = userService.updateUser(principal, userDTO);

        assertNull(result);
    }

    @Test
    void testUpdateUserDeletedUser() {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("test@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");

        User deletedUser = new User();
        deletedUser.setId(1);
        deletedUser.setFirstName("OldFirst");
        deletedUser.setLastName("OldLast");
        deletedUser.setDeletedAt(LocalDateTime.now());

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(deletedUser));

        UserDTO result = userService.updateUser(principal, userDTO);

        assertNull(result);
    }

    @Test
    void testSoftDeleteUser() {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("test@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        User user = new User();
        user.setId(1);

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.saveAndFlush(user)).thenReturn(user);

        assertDoesNotThrow(() -> userService.softDeleteUser(principal));
    }

    // @Test
    // void testSoftDeleteUserNotFound() {
    //     UserPrincipal principal = UserPrincipal.builder()
    //             .userId(1)
    //             .email("test@example.com")
    //             .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
    //             .build();

    //     when(userRepository.findById(principal.getUserId())).thenReturn(Optional.empty());

    //     assertDoesNotThrow(() -> userService.softDeleteUser(principal));
    // }

    @Test
    void testRestoreUser() {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("test@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        User user = new User();
        user.setId(1);
        user.setDeletedAt(LocalDateTime.now());

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(userMapper.userToUserDto(user)).thenReturn(new UserDTO());

        UserDTO result = userService.restoreUser(principal);

        assertNotNull(result);
    }

    // @Test
    // void testRestoreUserNotFound() {
    //     UserPrincipal principal = UserPrincipal.builder()
    //             .userId(1)
    //             .email("test@example.com")
    //             .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
    //             .build();

    //     when(userRepository.findById(principal.getUserId())).thenReturn(Optional.empty());

    //     UserDTO result = userService.restoreUser(principal);

    //     assertNull(result);
    // }

    @Test
    void testSoftDeleteEmployee() {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("test@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        User admin = new User();
        admin.setId(1);
        admin.setRole("admin");
        admin.setOrganisation(createOrganisation(1));

        User employee = new User();
        employee.setId(2);
        employee.setOrganisation(admin.getOrganisation());

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(admin));
        when(userRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(userRepository.saveAndFlush(employee)).thenReturn(employee);

        assertDoesNotThrow(() -> userService.softDeleteEmployee(principal, employee.getId()));
    }

    @Test
    void testSoftDeleteEmployeeNonAdmin() {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("test@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        User user = new User();
        user.setId(1);
        user.setRole("user");
        user.setOrganisation(createOrganisation(1));

        User employee = new User();
        employee.setId(2);
        employee.setOrganisation(user.getOrganisation());

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));

        assertThrows(Error.class, () -> userService.softDeleteEmployee(principal, employee.getId()));
    }

    @Test
    void testSoftDeleteEmployeeDifferentOrganisation() {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("test@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        User admin = new User();
        admin.setId(1);
        admin.setRole("admin");
        admin.setOrganisation(createOrganisation(1));

        User employee = new User();
        employee.setId(2);
        employee.setOrganisation(createOrganisation(2));

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(admin));
        when(userRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        assertThrows(Error.class, () -> userService.softDeleteEmployee(principal, employee.getId()));
    }

    private Organisation createOrganisation(Integer id) {
        Organisation organisation = new Organisation();
        organisation.setId(id);
        return organisation;
    }
}

