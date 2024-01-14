package ro.bynaus.nohs.controllers;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.models.LoginCredentialsDTO;
import ro.bynaus.nohs.models.LoginResponse;
import ro.bynaus.nohs.security.JwtIssuer;
import ro.bynaus.nohs.security.UserPrincipal;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    
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
}
