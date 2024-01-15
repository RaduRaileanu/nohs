package ro.bynaus.nohs.controllers;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.UserMapper;
import ro.bynaus.nohs.models.LoginCredentialsDTO;
import ro.bynaus.nohs.models.LoginResponse;
import ro.bynaus.nohs.models.RegisterResponse;
import ro.bynaus.nohs.models.UserDTO;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.JwtIssuer;
import ro.bynaus.nohs.security.UserPrincipal;
import ro.bynaus.nohs.services.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    // private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginCredentialsDTO loginCredentials){
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginCredentials.getEmail(), loginCredentials.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                            .map(grantedAuthority -> grantedAuthority.getAuthority())
                            .toList();

        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);
        return LoginResponse.builder()
                                .accessToken(token)
                                .build();
    }

    @PostMapping("/auth/register")
    public RegisterResponse createUser(@RequestBody UserDTO userDTO) {

        // if another user with the same email exists in the database, the registration process is aborted with a operation fail response
        if(userRepository.existsByEmail(userDTO.getEmail())){
            return RegisterResponse.builder()
                                .accessToken("")
                                .message("A user with the same email is already registered")
                                .successfulCompletion(false)
                                .build();
        }

        // encode the user's password
        String encodedPassword = passwordEncoder.encode(userDTO.getEmail());
        userDTO.setPassword(encodedPassword);
        
        // save the new user to the database
        User user = userMapper.userDtoToUser(userDTO);
        User savedUser = userRepository.saveAndFlush(user);

        // create a principal for this user
        UserPrincipal principal = UserPrincipal.builder()
                                                .userId(savedUser.getId())
                                                .email(user.getEmail())
                                                .password(user.getPassword())
                                                .authorities(userDTO.getAuthorities())
                                                .build();

        var roles = principal.getAuthorities().stream()
                                                .map(grantedAuthority -> grantedAuthority.getAuthority())
                                                .toList();

        // issue a JWT for the user to be used on future api requests
        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);

        // respond with the JWT as well as other registration information
        return RegisterResponse.builder()
                                .accessToken(token)
                                .message("New user registered successfully")
                                .registeredUserDTO(userMapper.userToUserDto(savedUser))
                                .successfulCompletion(true)
                                .build();
    }
    
}
