package ro.bynaus.nohs.services;

import java.util.Optional;

import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.models.UserDTO;
import ro.bynaus.nohs.security.UserPrincipal;

public interface UserService {
    
    public Optional<User> findByEmail(String email);

    public UserDTO updateUser(UserPrincipal principal, UserDTO userDTO);

    public void softDeleteUser(UserPrincipal principal);

    public void softDeleteEmployee(UserPrincipal principal, Integer userId);

    public UserDTO restoreUser(UserPrincipal principal);
}
