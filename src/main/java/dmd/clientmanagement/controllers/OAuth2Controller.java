package dmd.clientmanagement.controllers;

import dmd.clientmanagement.dto.AuthResponse;
import dmd.clientmanagement.dto.OAuth2TokenRequest;
import dmd.clientmanagement.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2UserService oAuth2UserService;

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> exchangeToken(@RequestBody OAuth2TokenRequest tokenRequest) {
        // Validate token with Google
        OAuth2User oAuth2User = oAuth2UserService.validateTokenWithGoogle(tokenRequest);

        // Process user and generate JWT
        AuthResponse authResponse = oAuth2UserService.processOAuth2User(oAuth2User);

        return ResponseEntity.ok(authResponse);
    }
}

