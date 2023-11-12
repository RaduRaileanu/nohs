package spring6.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import spring6.bynaus.nohs.domain.Post;
import spring6.bynaus.nohs.models.PostDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-12T15:38:20+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
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
        postDTO.origContent( post.getOrigContent() );
        postDTO.isHateSpeech( post.getIsHateSpeech() );
        postDTO.redactedContent( post.getRedactedContent() );
        postDTO.justification( post.getJustification() );

        return postDTO.build();
    }

    @Override
    public Post postDTOToPost(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        post.id( postDTO.getId() );
        post.origContent( postDTO.getOrigContent() );
        post.isHateSpeech( postDTO.getIsHateSpeech() );
        post.redactedContent( postDTO.getRedactedContent() );
        post.justification( postDTO.getJustification() );

        return post.build();
    }
}
