package ro.bynaus.nohs.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostDTO {

    private Integer id;

    private String originalContent;
    private String redactedContent;
    private Boolean hateSpeech;
    private String justification;

    private OrganisationDTO organisationDto;
    private UserDTO userDto;

    private LocalDateTime createdAt;
}
