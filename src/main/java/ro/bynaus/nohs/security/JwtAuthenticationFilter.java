package ro.bynaus.nohs.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;

    /**
     * Custom filter to handle authentication based on JWT (JSON Web Token) in the request headers.
     *
     * <p>This filter extracts the JWT from the "Authorization" header, decodes it, converts it into
     * a {@code UserPrincipal}, and sets it as the authenticated user in the security context.
     *
     * @param request     The HTTP request being processed.
     * @param response    The HTTP response being generated.
     * @param filterChain The filter chain for additional processing.
     * @throws ServletException If an exception occurs during the filter execution.
     * @throws IOException      If an I/O error occurs during the filter execution.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Extract, decode, and convert the JWT to UserPrincipal, then set it as the authenticated user
        extractTokenFromRequest(request)
                    .map(jwtDecoder::decode)
                    .map(jwtToPrincipalConverter::convert)
                    .map(UserPrincipalAuthenticationToken::new)
                    .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        
        // Continue with the filter chain after authentication processing    
        filterChain.doFilter(request, response);
    }
    
    /**
     * Extract the JWT (JSON Web Token) from the "Authorization" header of the HTTP request.
     *
     * <p>If the "Authorization" header is present and starts with "Bearer ", the token is extracted
     * and returned as an optional string. Otherwise, an empty optional is returned.
     *
     * @param request The HTTP request from which to extract the token.
     * @return An optional string containing the extracted JWT, or an empty optional if not present.
     */
    private Optional<String> extractTokenFromRequest(HttpServletRequest request){
        // Extract the token from the "Authorization" header if present
        var token = request.getHeader("Authorization");

        if(StringUtils.hasText(token) && token.startsWith("Bearer ")){
            // If the header is present and starts with "Bearer ", extract the token
            return Optional.of(token.substring(7));
        }
        // Return an empty optional if the token is not present in the header
        return Optional.empty();
    }
}
