package ro.bynaus.nohs.services;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.UserMapper;
import ro.bynaus.nohs.models.RegisterResponse;
import ro.bynaus.nohs.models.UserDTO;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.JwtIssuer;
import ro.bynaus.nohs.security.UserPrincipal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Finds the active user by email
     * 
     * @param email      The email of the user that has to be retrieved
     */
    @Override
    public Optional<User> findByEmail(String email) {
        
        List<User> userList = userRepository.findByEmail(email);

        if(userList.isEmpty()) return Optional.empty();

        // if the user has been soft deleted, return and empty Optional
        if(userList.getFirst().getDeletedAt() != null) return Optional.empty();
        
        return Optional.of(userList.getFirst());
    }

    @Override
    public UserDTO updateUser(UserPrincipal principal, UserDTO userDTO) {
       
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        // if no user was found or if the user has been soft deleted, return null
        if(user == null || user.getDeletedAt() != null) return null;

        // update the user property (properties) that have changed
        if(user.getFirstName() != userDTO.getFirstName()){
            user.setFirstName(userDTO.getFirstName());
        }

        if(user.getLastName() != userDTO.getLastName()){
            user.setLastName(userDTO.getLastName());
        }

        if(user.getEmail() != userDTO.getEmail()){
            user.setEmail(userDTO.getEmail());
        }

        if(user.getPassword() != userDTO.getPassword()){
            user.setPassword(userDTO.getPassword());
        }

        if(user.getRole() != userDTO.getRole()){
            user.setRole(userDTO.getRole());
        }

        User savedUser = userRepository.saveAndFlush(user);

        UserDTO savedUserDTO = userMapper.userToUserDto(savedUser);

        return savedUserDTO;
    }

    @Override
    public void softDeleteUser(UserPrincipal principal) {
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        user.setDeletedAt(LocalDateTime.now());

        userRepository.saveAndFlush(user);
    }

    @Override
    public UserDTO restoreUser(UserPrincipal principal) {
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        user.setDeletedAt(null);

        userRepository.saveAndFlush(user);

        UserDTO saveUserDTO = userMapper.userToUserDto(user);

        return saveUserDTO;
    }

    @Override
    public void softDeleteEmployee(UserPrincipal principal, Integer userId) throws Error {
       
        User admin = userRepository.findById(principal.getUserId()).orElse(null);
        User employee = userRepository.findById(userId).orElse(null);

        if(admin.getRole() != "admin"){
            throw new Error("The authenticated user is not an admin and cannot delete other employees of their organisation");
        }

        if(admin.getOrganisation().getId() != employee.getOrganisation().getId()){
            throw new Error("Employee works for a different organisation that the authenticated user's ");
        } 

        employee.setDeletedAt(LocalDateTime.now());

        userRepository.saveAndFlush(employee);
    }
    
}
