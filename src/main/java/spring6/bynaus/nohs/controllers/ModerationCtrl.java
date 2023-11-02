package spring6.bynaus.nohs.controllers;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import spring6.bynaus.nohs.domain.Post;
import spring6.bynaus.nohs.services.ModerateContent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
public class ModerationCtrl {
    private final ModerateContent moderateContent;

    public ModerationCtrl(ModerateContent moderateContent){
        this.moderateContent = moderateContent;
    }

    @RequestMapping("/api/get/mposts")
    public List<Post> getPosts(){
        return moderateContent.getModeratedPosts();
    }

    @RequestMapping("/api/get/mposts/{id}")
    public Post getPost(@PathVariable("id") UUID id){
        return moderateContent.getModeratedPost(id);
    }

    @PostMapping(value="/api/post/mposts")
    public ResponseEntity createPost(@RequestBody Post post) {
        
        Post savedPost = moderateContent.savePost(post);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/post/mposts" + savedPost.getId().toString());
        
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    
}
