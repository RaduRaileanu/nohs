package ro.bynaus.nohs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.bynaus.nohs.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
    
}
