package team10.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team10.user.repositories.UserRepository;

@Service
public class UserUtilityService {
    @Autowired
    private UserRepository userRepository;

    public boolean userExists(String username) {
        return userRepository.findByEmail(username) != null;
    }
}
