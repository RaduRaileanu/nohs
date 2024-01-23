package ro.bynaus.nohs.services;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.UserMapper;
import ro.bynaus.nohs.models.UserDTO;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.UserPrincipal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Retrieve a user by their email address, excluding soft-deleted users.
     *
     * <p>This method queries the UserRepository to find a list of users with the specified email.
     * If the list is empty or the first user has been soft deleted, it returns an empty Optional.
     * Otherwise, it returns an Optional containing the first non-deleted user with the specified email.
     *
     * @param email The email address of the user to retrieve.
     * @return An Optional containing the user with the specified email if found and not soft-deleted; otherwise, an empty Optional.
     */
    @Override
    public Optional<User> findByEmail(String email) {
        
        List<User> userList = userRepository.findByEmail(email);

        if(userList.isEmpty()) return Optional.empty();

        // if the user has been soft deleted, return and empty Optional
        if(userList.getFirst().getDeletedAt() != null) return Optional.empty();
        
        return Optional.of(userList.getFirst());
    }

    /**
     * Update the properties of the authenticated user based on the provided UserDTO.
     *
     * <p>This method fetches the User entity based on the UserPrincipal,
     * updates the user properties with non-null values from the provided UserDTO,
     * and saves the updated user to the database.
     *
     * @param principal The authenticated user's principal containing user details.
     * @param userDTO   The UserDTO containing the updated user properties.
     * @return A UserDTO containing the details of the updated user, or null if the user is not found or has been soft-deleted.
     */
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

    /**
     * Soft-delete the authenticated user by setting the 'deletedAt' timestamp to the current date and time.
     *
     * <p>This method fetches the User entity based on the UserPrincipal, sets the 'deletedAt' timestamp to the current date and time,
     * and saves the updated user to the database.
     *
     * @param principal The authenticated user's principal containing user details.
     */
    @Override
    public void softDeleteUser(UserPrincipal principal) {
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        user.setDeletedAt(LocalDateTime.now());

        userRepository.saveAndFlush(user);
    }

    /**
     * Restore the soft-deleted authenticated user by setting the 'deletedAt' timestamp to null.
     *
     * <p>This method fetches the User entity based on the UserPrincipal, sets the 'deletedAt' timestamp to null,
     * and saves the updated user to the database.
     *
     * @param principal The authenticated user's principal containing user details.
     * @return A UserDTO containing the details of the restored user.
     */
    @Override
    public UserDTO restoreUser(UserPrincipal principal) {
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        user.setDeletedAt(null);

        userRepository.saveAndFlush(user);

        UserDTO saveUserDTO = userMapper.userToUserDto(user);

        return saveUserDTO;
    }

    /**
     * Soft-delete an employee by setting the 'deletedAt' timestamp to the current date and time.
     *
     * <p>This method fetches the User entities for both the authenticated admin and the employee to be deleted,
     * checks admin privileges and organisation association, sets the 'deletedAt' timestamp for the employee to the current date and time,
     * and saves the updated employee to the database.
     *
     * @param principal The authenticated user's principal containing admin details.
     * @param userId    The ID of the employee to be soft-deleted.
     * @throws Error If the authenticated user is not an admin or if the employee works for a different organisation.
     */
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
