package ro.bynaus.nohs.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.integrations.OpenAiProperties;
import ro.bynaus.nohs.models.OpenAiResponse;

@Service
@RequiredArgsConstructor
public class OpenAiService {
    private final RestTemplate restTemplate;

    private final OpenAiProperties openAiProperties;

    /**
     * Call OpenAI's GPT service using a specified prompt.
     *
     * <p>This method sends a request to the OpenAI API's chat completions endpoint, using the provided
     * prompt as input. It includes necessary headers and authentication using the API key. The response
     * is processed to extract the message content, prompt tokens, and completion tokens.
     *
     * @param prompt The input prompt for which completion is requested.
     * @return An OpenAiResponse containing the message content, prompt tokens, and completion tokens
     *         extracted from the GPT service response.
     */
    public OpenAiResponse callGpt (String prompt){
        // Set necessary headers for the OpenAI API request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + openAiProperties.getApiKey());

        // Set the API endpoint URL and construct the request body with the provided prompt
        String url = "https://api.openai.com/v1/chat/completions";
        String requestBody = "{"
                + "\"model\":\"gpt-3.5-turbo\","
                + "\"messages\":["
                + "{\"role\":\"system\",\"content\":\"You are a vigilent assistant.\"},"
                + "{\"role\":\"user\",\"content\":\"" + prompt + "\"}"
                + "]}";

        // Create a HttpEntity with the request body and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make a POST request to the OpenAI API and retrieve the JSON response
        JsonNode response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JsonNode.class).getBody();

        // Extract relevant information from the API response and build an OpenAiResponse
        OpenAiResponse openAiResponse = OpenAiResponse.builder()
                                                        .message(response.at("/choices/0/message/content").asText())
                                                        .promptTokens(response.at("/choices/usage/prompt_tokens").asInt())
                                                        .completionTokens(response.at("/choices/usage/completion_tokens").asInt())        
                                                        .build();

        return openAiResponse;
    }
}
