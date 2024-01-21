package ro.bynaus.nohs.integrations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties("openai")
public class OpenAiProperties {
    
    private String apiKey;
}
