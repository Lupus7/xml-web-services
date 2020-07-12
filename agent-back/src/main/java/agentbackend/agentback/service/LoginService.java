package agentbackend.agentback.service;

import agentbackend.agentback.config.JwtProperties;
import agentbackend.agentback.controller.dto.LoginRequest;
import agentbackend.agentback.model.User;
import agentbackend.agentback.repository.UserRepository;
import agentbackend.agentback.soapClient.UserSoapClient;
import com.auth0.jwt.JWT;
import com.car_rent.agent_api.wsdl.GetAllUsersResponse;
import com.car_rent.agent_api.wsdl.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSoapClient userSoapClient;

    public String login(LoginRequest loginRequest) {
        GetAllUsersResponse response = userSoapClient.getAllUsersResponse();

        if (response != null && response.getUserDetails() != null) {
            for (UserDetails ud : response.getUserDetails()) {
                User user = userRepository.findByEmail(ud.getEmail());
                if (user == null) {
                    user = new User();
                    user.setAddress(ud.getAddress());
                    user.setApproved(ud.isApproved());
                    user.setBlocked(ud.isBlocked());
                    user.setBusinessNumber(ud.getBuisinessNumber());
                    user.setCompanyName(ud.getCompanyName());
                    user.setEmail(ud.getEmail());
                    user.setFirstName(ud.getFirstName());
                    user.setLastName(ud.getLastName());
                    user.setPassword(ud.getPassword());
                    user.setServiceId(ud.getId());
                }
                user.setCars(ud.getCars());
                user.setBookings(ud.getBookings());
                userRepository.save(user);
            }
        }


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
