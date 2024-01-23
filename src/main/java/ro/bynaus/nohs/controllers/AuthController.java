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
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Controller method for handling user authentication and login.
     * Endpoint: POST "/auth/login"
     *
     * @param loginCredentials The credentials provided by the user for login.
     * @return A response containing the access token upon successful login.
     */
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginCredentialsDTO loginCredentials){
        // Authenticate user
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginCredentials.getEmail(), loginCredentials.getPassword())
        );

        // Set the authentication in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get user principal details
        var principal = (UserPrincipal) authentication.getPrincipal();

        // Extract user roles
        var roles = principal.getAuthorities().stream()
                            .map(grantedAuthority -> grantedAuthority.getAuthority())
                            .toList();

        // Generate JWT token
        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);

        // Return login response with the generated access token
        return LoginResponse.builder()
                                .accessToken(token)
                                .build();
    }

    /**
     * Controller method for handling user registration.
     * Endpoint: POST "/auth/register"
     *
     * @param userDTO The information required for user registration.
     * @return A response containing the access token upon successful login.
     */
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
