package ro.bynaus.nohs.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostEvaluationDTO {
    private String originalContent;
    private String redactedContent;
    private String justification;
    private Boolean hateSpeech;
}
