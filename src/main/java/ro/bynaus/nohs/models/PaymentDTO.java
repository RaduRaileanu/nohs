package ro.bynaus.nohs.models;

import java.time.LocalDateTime;

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
public class PaymentDTO {
    
    private Integer id;

    private Double sum;
    private String invoiceNo;

    // private OrganisationDTO organisation;
    private UserDTO user;

    private LocalDateTime createdAt;
}
