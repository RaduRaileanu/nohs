package ro.bynaus.nohs.security;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtDecoder {

    private final JwtProperties properties;

    /**
     * Decode a JSON Web Token (JWT) and verify its signature using the specified secret key.
     *
     * <p>This property decodes a JWT by requiring the specified HMAC256 algorithm with the secret key.
     * It verifies the token's signature and returns the decoded JWT if the verification is successful.
     *
     * @param token The JWT to be decoded and verified.
     * @return The decoded JWT if the signature is valid.
     * @throws JWTVerificationException If the JWT verification fails, indicating an invalid or tampered token.
     */
    public DecodedJWT decode(String token){
        return JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
                    .build()
                    .verify(token);
    }
}
