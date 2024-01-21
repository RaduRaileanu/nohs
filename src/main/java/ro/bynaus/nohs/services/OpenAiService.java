package ro.bynaus.nohs.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.integrations.OpenAiProperties;

@Service
@RequiredArgsConstructor
public class OpenAiService {
    private final RestTemplate restTemplate;

    private final OpenAiProperties openAiProperties;

    public String evaluatePost (String prompt){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + openAiProperties.getApiKey());

        String url = "https://api.openai.com/v1/chat/completions";
        // String requestBody = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\": \"system\", \"content\":\"You are a helpful assistant.\"},{\"role\": \"user\", \"content\":\"" + prompt + "\"}]}";

        String requestBody = "{"
                + "\"model\":\"gpt-3.5-turbo\","
                + "\"messages\":["
                + "{\"role\":\"system\",\"content\":\"You are a vigilent assistant.\"},"
                + "{\"role\":\"user\",\"content\":\"" + prompt + "\"}"
                + "]}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();

        System.out.println(response);

        return response;
    }
}
