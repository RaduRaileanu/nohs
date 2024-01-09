package ro.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.bynaus.nohs.entities.BillingInfo;
import ro.bynaus.nohs.models.BillingInfoDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-09T07:42:57+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class BillingInfoMapperImpl implements BillingInfoMapper {

    @Override
    public BillingInfo billingInfoDtoToBillingInfo(BillingInfoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BillingInfo.BillingInfoBuilder billingInfo = BillingInfo.builder();

        billingInfo.id( dto.getId() );
        billingInfo.city( dto.getCity() );
        billingInfo.country( dto.getCountry() );
        billingInfo.street( dto.getStreet() );
        billingInfo.streetNo( dto.getStreetNo() );
        billingInfo.other( dto.getOther() );
        billingInfo.taxNo( dto.getTaxNo() );
        billingInfo.idNumber( dto.getIdNumber() );
        billingInfo.createdAt( dto.getCreatedAt() );
        billingInfo.updatedAt( dto.getUpdatedAt() );
        billingInfo.deletedAt( dto.getDeletedAt() );

        return billingInfo.build();
    }

    @Override
    public BillingInfoDTO billingInfoToBillingInfoDto(BillingInfo billingInfo) {
        if ( billingInfo == null ) {
            return null;
        }

        BillingInfoDTO.BillingInfoDTOBuilder billingInfoDTO = BillingInfoDTO.builder();

        billingInfoDTO.id( billingInfo.getId() );
        billingInfoDTO.city( billingInfo.getCity() );
        billingInfoDTO.country( billingInfo.getCountry() );
        billingInfoDTO.street( billingInfo.getStreet() );
        billingInfoDTO.streetNo( billingInfo.getStreetNo() );
        billingInfoDTO.other( billingInfo.getOther() );
        billingInfoDTO.taxNo( billingInfo.getTaxNo() );
        billingInfoDTO.idNumber( billingInfo.getIdNumber() );
        billingInfoDTO.createdAt( billingInfo.getCreatedAt() );
        billingInfoDTO.updatedAt( billingInfo.getUpdatedAt() );
        billingInfoDTO.deletedAt( billingInfo.getDeletedAt() );

        return billingInfoDTO.build();
    }
}
