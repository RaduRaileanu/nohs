package ro.bynaus.nohs.models;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Integer id;
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    private OrganisationDTO organisation;
    
    private SubscriptionDTO subscription;

    private BillingInfoDTO billingInfo;

    private Set<PaymentDTO> payments;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
