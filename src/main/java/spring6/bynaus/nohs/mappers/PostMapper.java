package spring6.bynaus.nohs.mappers;

import org.mapstruct.Mapper;

import spring6.bynaus.nohs.domain.Post;
import spring6.bynaus.nohs.models.PostDTO;

@Mapper
public interface PostMapper {
    PostDTO postToPostDTO(Post post);
    Post postDTOToPost(PostDTO postDTO);
}
