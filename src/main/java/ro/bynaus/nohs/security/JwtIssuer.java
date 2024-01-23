package ro.bynaus.nohs.security;

import java.util.List;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties jwtProperties;

    /**
     * Issue a JSON Web Token (JWT) for the specified user details and roles.
     *
     * <p>This method creates and signs a JWT using the provided user details and roles. The token includes
     * the user ID as the subject, the email as a claim, and a list of authorities (roles) as a claim.
     *
     * @param userId The ID of the user for whom the JWT is issued.
     * @param email  The email address associated with the user.
     * @param roles  The list of authorities (roles) granted to the user.
     * @return The issued JWT as a string.
     */
    public String issue(int userId, String email, List<String> roles){
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("email", email)
                .withClaim("authorities", roles)
                .sign( Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}
