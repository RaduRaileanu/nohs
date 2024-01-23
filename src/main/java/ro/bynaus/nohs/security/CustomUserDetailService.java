package ro.bynaus.nohs.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.services.UserService;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    /**
     * Load user details by username for authentication.
     *
     * @param username The username (email) for which user details are to be loaded.
     * @return A {@code UserDetails} object representing the authenticated user.
     * @throws UsernameNotFoundException If the user with the given username is not found.
     */
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
