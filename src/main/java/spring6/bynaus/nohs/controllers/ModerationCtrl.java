package spring6.bynaus.nohs.controllers;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import spring6.bynaus.nohs.domain.Post;
import spring6.bynaus.nohs.models.PostDTO;
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

    @GetMapping("/api/get/mposts")
    public List<PostDTO> getPosts(@RequestParam(required = false) Boolean postStatus){
        return moderateContent.getModeratedPosts(postStatus);
    }

    @RequestMapping("/api/get/mposts/{id}")
    public Optional<PostDTO> getPost(@PathVariable("id") UUID id){
        return moderateContent.getModeratedPost(id);
    }

    @PostMapping(value="/api/post/mposts")
    public ResponseEntity createPost(@RequestBody PostDTO post) {
        
        PostDTO savedPost = moderateContent.savePost(post);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/post/mposts" + savedPost.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    
}
