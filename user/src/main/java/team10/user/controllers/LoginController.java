package team10.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team10.user.config.JwtProperties;
import team10.user.models.dto.LoginRequest;
import team10.user.models.dto.RegistrationRequest;
import team10.user.services.LoginService;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = loginService.login(loginRequest);
        if (token.isEmpty())
            return ResponseEntity.badRequest().body("Invalid username/password.");

        return ResponseEntity.ok(JwtProperties.TOKEN_PREFIX + token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        if (loginService.register(registrationRequest))
            return ResponseEntity.ok("Registration successful");
        return ResponseEntity.badRequest().body("Bad request");
    }
}
