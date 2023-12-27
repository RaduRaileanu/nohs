package ro.bynaus.nohs.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    
    private Integer id;

    private Double sum;
    private String invoiceNo;

    private LocalDateTime createdAt;
}
