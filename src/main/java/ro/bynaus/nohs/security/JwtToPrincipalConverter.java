package ro.bynaus.nohs.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtToPrincipalConverter {
    
    /**
     * Convert a Decoded JSON Web Token (JWT) into a UserPrincipal.
     *
     * <p>This method takes a decoded JWT and extracts user details to build a UserPrincipal.
     * It retrieves the user ID from the subject, the email from the "email" claim, and authorities
     * (roles) from the "authorities" claim in the JWT.
     *
     * @param jwt The Decoded JWT to be converted.
     * @return The UserPrincipal containing user details extracted from the JWT.
     */
    public UserPrincipal convert(DecodedJWT jwt){
        return UserPrincipal.builder()
                            .userId(Integer.valueOf(jwt.getSubject()))
                            .email(jwt.getClaim("email").asString())
                            .authorities(extractAuthoritiesFromClaim(jwt))
                            .build();
    }

    /**
     * Extract authorities (roles) from the "authorities" claim in the Decoded JSON Web Token (JWT).
     *
     * <p>If the "authorities" claim is present and not null, it is converted to a list of
     * SimpleGrantedAuthority. If the claim is null or missing, an empty list is returned.
     *
     * @param jwt The Decoded JWT containing the "authorities" claim.
     * @return A list of SimpleGrantedAuthority representing the authorities (roles) from the JWT.
     */
    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt){
        // Extract "authorities" claim from the JWT
        var claim = jwt.getClaim("authorities");

        // If the claim is null or missing, return an empty list
        if(claim.isNull() || claim.isMissing()) return List.of();

        // Convert the claim to a list of SimpleGrantedAuthority
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
