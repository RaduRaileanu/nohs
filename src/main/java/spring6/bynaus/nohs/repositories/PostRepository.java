package spring6.bynaus.nohs.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import spring6.bynaus.nohs.domain.Post;

public interface PostRepository extends JpaRepository<Post, UUID>{
    
}
