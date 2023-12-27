package ro.bynaus.nohs.models;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganisationDTO {

    private Integer id;
    private String name;
    private String code;

    private SubscriptionDTO subscription;

    private Set<PaymentDTO> payments;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    
    private LocalDateTime deletedAt;
}
