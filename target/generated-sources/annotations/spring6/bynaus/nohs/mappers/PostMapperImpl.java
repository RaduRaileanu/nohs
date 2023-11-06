package spring6.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import spring6.bynaus.nohs.domain.Post;
import spring6.bynaus.nohs.models.PostDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-05T12:27:54+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230814-2020, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDTO postToPostDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO.PostDTOBuilder postDTO = PostDTO.builder();

        postDTO.id( post.getId() );
        postDTO.justification( post.getJustification() );
        postDTO.origContent( post.getOrigContent() );
        postDTO.redactedContent( post.getRedactedContent() );

        return postDTO.build();
    }

    @Override
    public Post postDTOToPost(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        post.id( postDTO.getId() );
        post.isHateSpeech( postDTO.getIsHateSpeech() );
        post.justification( postDTO.getJustification() );
        post.origContent( postDTO.getOrigContent() );
        post.redactedContent( postDTO.getRedactedContent() );

        return post.build();
    }
}
