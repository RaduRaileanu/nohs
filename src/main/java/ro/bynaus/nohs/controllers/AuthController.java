package ro.bynaus.nohs.controllers;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.models.LoginCredentialsDTO;
import ro.bynaus.nohs.models.LoginResponse;
import ro.bynaus.nohs.security.JwtIssuer;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtIssuer;
    
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginCredentialsDTO loginCredentials){
        var token = jwtIssuer.issue(1, loginCredentials.getEmail(), List.of("admin"));
        return LoginResponse.builder()
                                .accessToken(token)
                                .build();
    }
}
