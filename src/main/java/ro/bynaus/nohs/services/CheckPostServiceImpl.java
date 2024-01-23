package ro.bynaus.nohs.services;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.models.CheckPostInfo;
import ro.bynaus.nohs.models.OpenAiResponse;
import ro.bynaus.nohs.models.PostDTO;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CheckPostServiceImpl implements CheckPostService {

    private final OpenAiService openAiService;

    /**
     * Run a post check using OpenAI's GPT service.
     *
     * <p>This method constructs a prompt based on the provided service and original post content,
     * calls the OpenAI service, processes the response, and builds a CheckPostInfo object containing
     * the results and cost of the post check.
     *
     * @param service   The Service object representing the type of post check to be performed.
     * @param origPost  The original content of the post to be checked.
     * @return A CheckPostInfo object containing the results of the post check, including the redacted
     *         content, hate speech status, cost, and other relevant information.
     */
    @Override
    public CheckPostInfo runPostCheck(Service service, String origPost) {

        // Construct a prompt based on the service and original post content
        String prompt = service.getMessage() + "'" + origPost + "'";

        // Call OpenAI service and retrieve the response
        OpenAiResponse response = openAiService.callGpt(prompt);

        // Calculate the cost based on token counts
        Double cost = response.getPromptTokens() * 0.001 + response.getCompletionTokens() * 0.002;

        // Process the response message
        String[] parts = response.getMessage().split("\\d+\\.");

        // Handle different service types
        if (service.getId() == 1) {
            // Extract information for Service type 1
            Boolean isHateSpeech = "no".equals(parts[1].trim().toLowerCase()) ? false : true;
            String hateSpeech = parts[3].replaceAll("^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");
            String redactedContent = isHateSpeech ? origPost.replace(hateSpeech, "*****") : origPost;
            String language = parts[4].trim().toLowerCase();
            String justification = null;
            // If the post contains hate speech and was not written in English, translate the justification in the language in which the post was written and update cost
            if(isHateSpeech && !"english".equals(language)){
                prompt = "Translate the following text in " + language + ": '" + parts[2].trim() + "'";

                OpenAiResponse translationResponse = openAiService.callGpt(prompt);

                justification = translationResponse.getMessage();
                cost += translationResponse.getPromptTokens() * 0.001 + translationResponse.getCompletionTokens() * 0.002;
            }
            
            // Build PostDTO and CheckPostInfo
            PostDTO postDto = PostDTO.builder()
                                        .originalContent(origPost)
                                        .redactedContent(redactedContent)
                                        .justification(justification)
                                        .hateSpeech(isHateSpeech)
                                        .build();
            CheckPostInfo checkPostInfo = CheckPostInfo.builder()
                                                        .cost(cost)
                                                        .postDTO(postDto)
                                                        .build();

            return checkPostInfo;
        }
        else if(service.getId() == 2){
            // Extract information for Service type 2
            Boolean isHateSpeech = "no".equals(parts[1].trim().toLowerCase()) ? false : true;
            String hateSpeech = parts[2].replaceAll("^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");
            String redactedContent = isHateSpeech ? origPost.replace(hateSpeech, "*****") : origPost;
            
            // Build PostDTO and CheckPostInfo
            PostDTO postDto = PostDTO.builder()
                                        .originalContent(origPost)
                                        .redactedContent(redactedContent)
                                        .hateSpeech(isHateSpeech)
                                        .build();
            CheckPostInfo checkPostInfo = CheckPostInfo.builder()
                                                        .cost(cost)
                                                        .postDTO(postDto)
                                                        .build();

            return checkPostInfo;
        }

        else {
            // Extract information for Service type 3
            Boolean isHateSpeech = "no".equals(parts[0].trim().toLowerCase()) ? false : true;
            
            // Build PostDTO and CheckPostInfo
            PostDTO postDto = PostDTO.builder()
                                        .originalContent(origPost)
                                        .hateSpeech(isHateSpeech)
                                        .build();
            CheckPostInfo checkPostInfo = CheckPostInfo.builder()
                                                        .cost(cost)
                                                        .postDTO(postDto)
                                                        .build();

            return checkPostInfo;
        }
    }
    
}
