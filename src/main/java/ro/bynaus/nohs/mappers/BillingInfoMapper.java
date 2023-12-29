package ro.bynaus.nohs.mappers;

import org.mapstruct.Mapper;

import ro.bynaus.nohs.entities.BillingInfo;
import ro.bynaus.nohs.models.BillingInfoDTO;

@Mapper
public interface BillingInfoMapper {
    BillingInfo billingInfoDtoToBillingInfo(BillingInfoDTO dto);
    BillingInfoDTO billingInfoToBillingInfoDto(BillingInfo billingInfo);
}
