package ro.bynaus.nohs.services;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.models.CheckPostInfo;
import ro.bynaus.nohs.models.PostDTO;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CheckPostServiceImpl implements CheckPostService {

    private final OpenAiService openAiService;

    @Override
    public CheckPostInfo runPostCheck(Service service, String origPost) {

        String prompt = service.getMessage() + "'" + origPost + "'";

        openAiService.evaluatePost(prompt);

        String redactedContent = origPost.replace("Romanians are scumbags", "******");
        String justification = "Affirming hatred towards a nation is not protected by freedom of speech";
        if (service.getId() == 1) {
            PostDTO postDto = PostDTO.builder()
                                        .originalContent(origPost)
                                        .redactedContent(redactedContent)
                                        .justification(justification)
                                        .hateSpeech(true)
                                        .build();
            CheckPostInfo checkPostInfo = CheckPostInfo.builder()
                                                        .cost(0.05)
                                                        .postDTO(postDto)
                                                        .build();

            return checkPostInfo;
        }

        else if(service.getId() == 2){
            PostDTO postDto = PostDTO.builder()
                                        .originalContent(origPost)
                                        .redactedContent(redactedContent)
                                        .hateSpeech(true)
                                        .build();
            CheckPostInfo checkPostInfo = CheckPostInfo.builder()
                                                        .cost(0.03)
                                                        .postDTO(postDto)
                                                        .build();

            return checkPostInfo;
        }

        else {
            PostDTO postDto = PostDTO.builder()
                                        .originalContent(origPost)
                                        .hateSpeech(true)
                                        .build();
            CheckPostInfo checkPostInfo = CheckPostInfo.builder()
                                                        .cost(0.01)
                                                        .postDTO(postDto)
                                                        .build();

            return checkPostInfo;
        }
    }
    
}
