package team10.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team10.user.models.User;
import team10.user.models.dto.UserAuthInfo;
import team10.user.repositories.UserRepository;
import team10.user.util.UserAuthInfoMapper;

import java.util.Optional;

@Service
public class UserUtilityService {
    @Autowired
    private UserRepository userRepository;

    public boolean userExists(String username) {
        return userRepository.findByEmail(username) != null;
    }

    public UserAuthInfo getAuthInfo(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null)
            return null;

        return UserAuthInfoMapper.toDTO(user);
    }

    public String getUserEmail(Long id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get().getEmail();
        return null;
    }

    public ResponseEntity<String> getUserRole(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return ResponseEntity.badRequest().body("User doesnt exist!");
        if (user.getAuthorities() == null || user.getAuthorities().equals(""))
            return ResponseEntity.badRequest().body("User doesnt have authorities!");
        String[] privileges = user.getAuthorities().split(";");
        return ResponseEntity.ok(privileges[0]);

    }
}
