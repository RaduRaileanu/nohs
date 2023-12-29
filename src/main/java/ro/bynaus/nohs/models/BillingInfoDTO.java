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
public class BillingInfoDTO {

    private Integer id;

    private String city;
    private String country;
    private String street;
    private String streetNo;
    private String other;
    private String taxNo;
    private String idNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    private LocalDateTime deletedAt;
}
