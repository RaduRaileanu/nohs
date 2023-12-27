package ro.bynaus.nohs.mappers;

import org.mapstruct.Mapper;

import ro.bynaus.nohs.entities.Payment;
import ro.bynaus.nohs.models.PaymentDTO;

@Mapper
public interface PaymentMapper {
    Payment paymentMapperDtoToPayment(PaymentDTO dto);
    PaymentDTO paymentToPaymentDTO(Payment payment);
}
