package ro.bynaus.nohs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ro.bynaus.nohs.entities.Post;
import ro.bynaus.nohs.models.PostDTO;

@Mapper
public interface PostMapper {
    @Mapping(target = "organisation.posts", ignore = true)
    @Mapping(target = "user.posts", ignore = true)
    Post postDtoToPost(PostDTO dto);
    PostDTO postToPostDto(Post post);
}
