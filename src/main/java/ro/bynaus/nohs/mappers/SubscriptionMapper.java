package ro.bynaus.nohs.mappers;

import org.mapstruct.Mapper;

import ro.bynaus.nohs.entities.Subscription;
import ro.bynaus.nohs.models.SubscriptionDTO;

@Mapper
public interface SubscriptionMapper {
    Subscription SubscriptionDtoToSubscription(SubscriptionDTO dto);
    SubscriptionDTO subcriptionToSubscriptionDto(Subscription subscription);
}
