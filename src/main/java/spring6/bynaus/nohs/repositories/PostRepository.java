package spring6.bynaus.nohs.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring6.bynaus.nohs.domain.Post;
import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, UUID>{
    List<Post> findAllByIsHateSpeech(boolean isHateSpeech);
    // List<Post> findAllByIsHateSpeechAndOrigContentIsLikeIgnoreCase(boolean isHateSpeech, String origContent);
}
