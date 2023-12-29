package ro.bynaus.nohs.models;

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
public class SubscriptionDTO {
    private Integer id;

    private ServiceDTO service;

    private Integer trialRequests;
    private Double ballance;
}
