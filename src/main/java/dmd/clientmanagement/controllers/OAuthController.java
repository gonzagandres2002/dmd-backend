package dmd.clientmanagement.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {
    @GetMapping("login/oauth2/code/{registrationId}")
    public ResponseEntity<?> handleCallback(@RequestParam("code") String code) {
        System.out.println("Authorization Code: " + code);
        return ResponseEntity.ok("Code received");
    }
}
