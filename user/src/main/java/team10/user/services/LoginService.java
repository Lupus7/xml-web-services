package team10.user.services;

import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team10.user.config.JwtProperties;
import team10.user.models.User;
import team10.user.models.dto.LoginRequest;
import team10.user.models.dto.UserAuthInfo;
import team10.user.repositories.UserRepository;

import java.net.URI;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getUsername());
        if (user == null)
            return "";

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            return "";

        return JWT.create()
                .withSubject(loginRequest.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
    }
}
