package team10.user.services;

import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team10.user.config.JwtProperties;
import team10.user.models.Privilege;
import team10.user.models.Role;
import team10.user.models.User;
import team10.user.models.dto.LoginRequest;
import team10.user.models.dto.NewAgentDTO;
import team10.user.models.dto.Placeholder;
import team10.user.models.dto.RegistrationRequest;
import team10.user.proxy.RentProxy;
import team10.user.repositories.RoleRepository;
import team10.user.repositories.UserRepository;
import team10.user.util.UserMapper;

import java.util.Date;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private RentProxy rentProxy;

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getUsername());
        if (user == null)
            return "";

        privilegeService.addDefaultPrivileges(user);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            return "";

        return JWT.create()
                .withSubject(loginRequest.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
    }

    public boolean register(RegistrationRequest registrationRequest) {
        User user = userRepository.findByEmail(registrationRequest.getEmail());
        if (user != null)
            return false;

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());

        registrationRequest.setPassword(encodedPassword);

        user = UserMapper.toEntity(registrationRequest);

        if (roleRepository.findByName(user.getRoles().get(0)) == null)
            return false;

        userRepository.save(user);

        ResponseEntity<String> responseEntity = rentProxy.createCart(user.getEmail() + ";MASTER", new Placeholder());

        if (responseEntity.getStatusCode().isError())
            return false;

        userRepository.save(user);
        return true;
    }
}
