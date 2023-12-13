package spring6.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import spring6.bynaus.nohs.domain.Post;
import spring6.bynaus.nohs.models.PostDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-13T09:02:06+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
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
        postDTO.isHateSpeech( post.getIsHateSpeech() );
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
