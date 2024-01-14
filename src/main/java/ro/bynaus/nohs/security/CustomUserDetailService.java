package ro.bynaus.nohs.security;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.services.UserService;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user = userService.findByEmail(username).orElseThrow();
        return UserPrincipal.builder()
                            .userId(user.getId())
                            .email(user.getEmail())
                            .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
                            .password(user.getPassword())
                            .build();
    }
    
}
