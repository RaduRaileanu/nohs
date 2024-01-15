package ro.bynaus.nohs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.bynaus.nohs.entities.Post;
import ro.bynaus.nohs.entities.User;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
}
