package agentbackend.agentback.service;

import agentbackend.agentback.config.JwtProperties;
import agentbackend.agentback.controller.dto.LoginRequest;
import agentbackend.agentback.model.User;
import agentbackend.agentback.repository.UserRepository;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

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
