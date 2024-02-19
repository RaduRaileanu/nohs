package ro.bynaus.nohs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    /**
     * Bean definition for CorsFilter to configure CORS settings.
     *
     * @return CorsFilter bean with CORS configuration
     */
    @Bean
    public CorsFilter corsFilter(){
        // Create a new CorsConfiguration instance
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Allow requests from the specified origin
        corsConfiguration.addAllowedOrigin("http://localhost:5173");

        // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        corsConfiguration.addAllowedMethod("*");

        // Allow all request headers
        corsConfiguration.addAllowedHeader("*");

        // Create a new UrlBasedCorsConfigurationSource instance
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Register the CorsConfiguration for all paths
        source.registerCorsConfiguration("/**", corsConfiguration);

        // Create and return a new CorsFilter instance with the configured CorsConfiguration
        return new CorsFilter(source);
    }
}
