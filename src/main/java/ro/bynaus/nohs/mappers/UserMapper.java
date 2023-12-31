package ro.bynaus.nohs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.models.UserDTO;


@Mapper(uses = {PaymentMapper.class, OrganisationMapper.class})

public interface UserMapper {
    @Mapping(target = "organisation.users", ignore = true)
    @Mapping(target = "organisation.payments", ignore = true)
    @Mapping(target = "organisation.posts", ignore = true)
    @Mapping(target = "posts", ignore = true)
    User userDtoToUser (UserDTO dto);
    UserDTO userToUserDto (User user);
}
