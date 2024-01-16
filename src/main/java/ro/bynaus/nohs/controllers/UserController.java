package ro.bynaus.nohs.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.models.UserDTO;
import ro.bynaus.nohs.security.UserPrincipal;
import ro.bynaus.nohs.services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Component
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/user")
    public UserDTO updateUser(@AuthenticationPrincipal UserPrincipal principal, @RequestBody UserDTO userDTO) {
        
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        
        return userService.updateUser(principal, userDTO);
    }

    @DeleteMapping("/user")
    public void softDeleteUser(@AuthenticationPrincipal UserPrincipal principal){
        userService.softDeleteUser(principal);
    }
    
}
