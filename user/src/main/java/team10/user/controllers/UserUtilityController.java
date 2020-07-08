package team10.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team10.user.models.dto.UserAuthInfo;
import team10.user.services.UserUtilityService;

@RestController
@RequestMapping("util")
public class UserUtilityController {
    @Autowired
    private UserUtilityService userUtilityService;

    @GetMapping("exists/{email}")
    public ResponseEntity<Boolean> userExists(@PathVariable("email") String email) {
        return ResponseEntity.ok(userUtilityService.userExists(email));
    }

    @GetMapping("auth/{email}")
    public ResponseEntity<UserAuthInfo> getAuthInfo(@PathVariable("email") String email) {
        UserAuthInfo userAuthInfo = userUtilityService.getAuthInfo(email);
        if (userAuthInfo == null)
            return ResponseEntity.badRequest().body(null);
        return ResponseEntity.ok(userAuthInfo);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<String> getUserEmail(@PathVariable("id") Long id) {
        String email = userUtilityService.getUserEmail(id);
        if (email != null)
            return ResponseEntity.ok(email);
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping(value = "/role/{email}")
    public ResponseEntity<String> getUserRole(@PathVariable("email") String email) {
        return userUtilityService.getUserRole(email);
    }
}
