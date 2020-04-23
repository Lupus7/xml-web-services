package team10.authenticationapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping("admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("hello admin :D");
    }

    @GetMapping("company")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<String> company() {
        return ResponseEntity.ok("hello company :D");
    }

    @GetMapping("authenticated")
    public ResponseEntity<String> authenticated() {
        return ResponseEntity.ok("hello authenticated user :D");
    }

    @GetMapping("")
    public ResponseEntity<String> anyone() {
        return ResponseEntity.ok("hello :D");
    }
}
