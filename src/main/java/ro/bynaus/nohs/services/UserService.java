package ro.bynaus.nohs.services;

import java.util.Optional;

import ro.bynaus.nohs.entities.User;

public interface UserService {
    
    public Optional<User> findByEmail(String email);
}
