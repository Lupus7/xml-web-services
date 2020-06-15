package agentbackend.agentback.controller;

import agentbackend.agentback.config.JwtProperties;
import agentbackend.agentback.controller.dto.LoginRequest;
import agentbackend.agentback.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
