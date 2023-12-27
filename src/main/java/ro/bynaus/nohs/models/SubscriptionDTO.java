package ro.bynaus.nohs.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDTO {
    private Integer id;

    private ServiceDTO service;

    private Integer trialRequests;
    private Double ballance;
}
