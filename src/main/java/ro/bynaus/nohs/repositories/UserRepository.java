package ro.bynaus.nohs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.bynaus.nohs.entities.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findFirstByOrderByIdAsc();
    List<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
