package ro.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.bynaus.nohs.entities.BillingInfo;
import ro.bynaus.nohs.models.BillingInfoDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-11T07:25:28+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class BillingInfoMapperImpl implements BillingInfoMapper {

    @Override
    public BillingInfo billingInfoDtoToBillingInfo(BillingInfoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BillingInfo.BillingInfoBuilder billingInfo = BillingInfo.builder();

        billingInfo.city( dto.getCity() );
        billingInfo.country( dto.getCountry() );
        billingInfo.createdAt( dto.getCreatedAt() );
        billingInfo.deletedAt( dto.getDeletedAt() );
        billingInfo.id( dto.getId() );
        billingInfo.idNumber( dto.getIdNumber() );
        billingInfo.other( dto.getOther() );
        billingInfo.street( dto.getStreet() );
        billingInfo.streetNo( dto.getStreetNo() );
        billingInfo.taxNo( dto.getTaxNo() );
        billingInfo.updatedAt( dto.getUpdatedAt() );

        return billingInfo.build();
    }

    @Override
    public BillingInfoDTO billingInfoToBillingInfoDto(BillingInfo billingInfo) {
        if ( billingInfo == null ) {
            return null;
        }

        BillingInfoDTO.BillingInfoDTOBuilder billingInfoDTO = BillingInfoDTO.builder();

        billingInfoDTO.city( billingInfo.getCity() );
        billingInfoDTO.country( billingInfo.getCountry() );
        billingInfoDTO.createdAt( billingInfo.getCreatedAt() );
        billingInfoDTO.deletedAt( billingInfo.getDeletedAt() );
        billingInfoDTO.id( billingInfo.getId() );
        billingInfoDTO.idNumber( billingInfo.getIdNumber() );
        billingInfoDTO.other( billingInfo.getOther() );
        billingInfoDTO.street( billingInfo.getStreet() );
        billingInfoDTO.streetNo( billingInfo.getStreetNo() );
        billingInfoDTO.taxNo( billingInfo.getTaxNo() );
        billingInfoDTO.updatedAt( billingInfo.getUpdatedAt() );

        return billingInfoDTO.build();
    }
}
