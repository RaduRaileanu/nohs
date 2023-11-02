package spring6.bynaus.lomboktest.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import spring6.bynaus.nohs.models.PersonDTO;
import spring6.bynaus.nohs.models.PostDTO;

@Slf4j
@Service
public class ModerateContentImpl implements ModerateContent{

    private Map<UUID, PostDTO> postMap;

    public ModerateContentImpl(){
        this.postMap = new HashMap<>();

        PostDTO post1 = PostDTO.builder()
                        .id(UUID.randomUUID())
                        .origContent("I like Spring. I hate these stupid Romanians")
                        .author(new PersonDTO())
                        .isHateSpeech(true)
                        .redactedContent("I like SPring. ****")
                        .justification("Saying that you hate all people belonging to a nation is a form o bigotism not acceptable unde freedom of speech principles")
                        .build();

        PostDTO post2 =PostDTO.builder()
                    .id(UUID.randomUUID())
                    .origContent("I like Spring. I hate filthy stupid Slavs")
                    .author(new PersonDTO())
                    .isHateSpeech(true)
                    .redactedContent("I like SPring. ****")
                    .justification("Saying that you hate all people belonging to a group is a form o bigotism not acceptable unde freedom of speech principles")
                    .build();
        
        this.postMap.put(post1.getId(), post1);
        this.postMap.put(post2.getId(), post2);
    }

    @Override
    public PostDTO getModeratedPost(UUID id){
        log.debug(("Get moderated post - in service. Id: " + id.toString()));

        return this.postMap.get(id);
    }

    @Override
    public List<PostDTO> getModeratedPosts(){
        return new ArrayList<>(postMap.values());
    }

    @Override
    public PostDTO savePost(PostDTO post){
        PostDTO savedPost = PostDTO.builder()
                            .id(UUID.randomUUID())
                            .origContent(post.getOrigContent())
                            .author(post.getAuthor())
                            .isHateSpeech(post.isHateSpeech())
                            .redactedContent(post.getRedactedContent())
                            .justification(post.getJustification())
                            .build();

        postMap.put(savedPost.getId(), savedPost);

        return savedPost;
    }
}
