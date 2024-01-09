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

    public String issue(int userId, String email, List<String> roles){
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("email", email)
                .withClaim("authorities", roles)
                .sign( Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}
