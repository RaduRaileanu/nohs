package ro.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.Post;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.models.PostDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-11T07:25:29+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post postDtoToPost(PostDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        post.createdAt( dto.getCreatedAt() );
        post.hateSpeech( dto.getHateSpeech() );
        post.id( dto.getId() );
        post.justification( dto.getJustification() );
        post.originalContent( dto.getOriginalContent() );
        post.redactedContent( dto.getRedactedContent() );
        post.organisation( postDTOToOrganisation( dto ) );
        post.user( postDTOToUser( dto ) );

        return post.build();
    }

    @Override
    public PostDTO postToPostDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO.PostDTOBuilder postDTO = PostDTO.builder();

        postDTO.createdAt( post.getCreatedAt() );
        postDTO.hateSpeech( post.getHateSpeech() );
        postDTO.id( post.getId() );
        postDTO.justification( post.getJustification() );
        postDTO.originalContent( post.getOriginalContent() );
        postDTO.redactedContent( post.getRedactedContent() );

        return postDTO.build();
    }

    protected Organisation postDTOToOrganisation(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        Organisation.OrganisationBuilder organisation = Organisation.builder();

        return organisation.build();
    }

    protected User postDTOToUser(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        return user.build();
    }
}
