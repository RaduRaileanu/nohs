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

    @Override
    public CheckPostInfo runPostCheck(Service service, String origPost) {

        String prompt = service.getMessage() + "'" + origPost + "'";

        OpenAiResponse response = openAiService.evaluatePost(prompt);

        Double cost = response.getPromptTokens() * 0.001 + response.getCompletionTokens() * 0.002;

        String[] parts = response.getMessage().split("\\d+\\.");

        if (service.getId() == 1) {
            Boolean isHateSpeech = "no".equals(parts[1].trim().toLowerCase()) ? false : true;
            String hateSpeech = parts[3].replaceAll("^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");
            String redactedContent = isHateSpeech ? origPost.replace(hateSpeech, "*****") : origPost;
            PostDTO postDto = PostDTO.builder()
                                        .originalContent(origPost)
                                        .redactedContent(redactedContent)
                                        .justification(isHateSpeech ? parts[2].trim(): null)
                                        .hateSpeech(isHateSpeech)
                                        .build();
            CheckPostInfo checkPostInfo = CheckPostInfo.builder()
                                                        .cost(cost)
                                                        .postDTO(postDto)
                                                        .build();

            return checkPostInfo;
        }

        else if(service.getId() == 2){
            Boolean isHateSpeech = "no".equals(parts[1].trim().toLowerCase()) ? false : true;
            String hateSpeech = parts[3].replaceAll("^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");
            String redactedContent = isHateSpeech ? origPost.replace(hateSpeech, "*****") : origPost;
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
            Boolean isHateSpeech = "no".equals(parts[0].trim().toLowerCase()) ? false : true;
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
