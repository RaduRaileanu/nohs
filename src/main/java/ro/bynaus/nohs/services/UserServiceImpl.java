package ro.bynaus.nohs.services;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        
        List<User> userList = userRepository.findByEmail(email);

        if(userList.isEmpty()) return Optional.empty();
        
        return Optional.of(userList.getFirst());
    }
    
}
