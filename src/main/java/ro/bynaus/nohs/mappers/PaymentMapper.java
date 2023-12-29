package ro.bynaus.nohs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ro.bynaus.nohs.entities.Payment;
import ro.bynaus.nohs.models.PaymentDTO;

@Mapper
public interface PaymentMapper {
    @Mapping(target = "organisation", ignore = true)
    @Mapping(target = "user", ignore = true)
    Payment paymentDtoToPayment(PaymentDTO dto);
    PaymentDTO paymentToPaymentDTO(Payment payment);
}
