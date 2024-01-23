package ro.bynaus.nohs.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.models.UserDTO;
import ro.bynaus.nohs.security.UserPrincipal;
import ro.bynaus.nohs.services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Component
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    /**
     * Update the details of the authenticated user.
     *
     * @param principal The authenticated user principal.
     * @param userDTO   The UserDTO containing the updated user details.
     * @return UserDTO representing the updated user details.
     */
    @PostMapping("/user")
    public UserDTO updateUser(@AuthenticationPrincipal UserPrincipal principal, @RequestBody UserDTO userDTO) {
        
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        
        return userService.updateUser(principal, userDTO);
    }

    /**
     * Soft delete the account of the authenticated user.
     *
     * @param principal The authenticated user principal.
     * @return ResponseEntity with no content if successful.
     */
    @DeleteMapping("/user")
    public ResponseEntity<Void> softDeleteAccount(@AuthenticationPrincipal UserPrincipal principal){
        userService.softDeleteUser(principal);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    /**
     * Soft delete an employee by their ID, performed by the authenticated user.
     *
     * @param principal The authenticated user principal.
     * @param userId    The ID of the user to be deleted.
     * @return ResponseEntity with no content if successful, else returns a not found response.
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> softDeleteEmployee(@AuthenticationPrincipal UserPrincipal principal, @PathVariable("userId") Integer userId){
        userService.softDeleteEmployee(principal, userId);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
