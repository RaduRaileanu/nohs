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
public class OrganisationDTO {

    private Integer id;
    private String name;
    private String code;

    private SubscriptionDTO subscription;
    private BillingInfoDTO billingInfo;

    // private Set<PaymentDTO> payments;
    // private Set<UserWithoutOrgDTO> users;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    
    private LocalDateTime deletedAt;
}
