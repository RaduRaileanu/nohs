package ro.bynaus.nohs.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationDTO {
    
    private Integer id;
    private String name;
    private String code;
    private String serviceType;
    private Integer trialRequests;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
