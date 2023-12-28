package ro.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.bynaus.nohs.entities.Payment;
import ro.bynaus.nohs.models.PaymentDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-28T11:09:42+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Autowired
    private OrganisationMapper organisationMapper;

    @Override
    public Payment paymentMapperDtoToPayment(PaymentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.createdAt( dto.getCreatedAt() );
        payment.id( dto.getId() );
        payment.invoiceNo( dto.getInvoiceNo() );
        payment.organisation( organisationMapper.organisationDtoToOrganisation( dto.getOrganisation() ) );
        payment.sum( dto.getSum() );

        return payment.build();
    }

    @Override
    public PaymentDTO paymentToPaymentDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDTO.PaymentDTOBuilder paymentDTO = PaymentDTO.builder();

        paymentDTO.createdAt( payment.getCreatedAt() );
        paymentDTO.id( payment.getId() );
        paymentDTO.invoiceNo( payment.getInvoiceNo() );
        paymentDTO.organisation( organisationMapper.organisationToOrganisationDTO( payment.getOrganisation() ) );
        paymentDTO.sum( payment.getSum() );

        return paymentDTO.build();
    }
}
