package dmd.clientmanagement.controllers;

import dmd.clientmanagement.dto.AuthResponse;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.security.JwtService;
import dmd.clientmanagement.service.AuthService;
import dmd.clientmanagement.dto.LoginRequest;
import dmd.clientmanagement.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        // Extract user information from the token
        String username = jwtService.validateTokenAndGetSubject(token);

        // Find the user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Activate the user
        user.setEmailVerified(true);
        userRepository.save(user);

        return ResponseEntity.ok("Email verified successfully! You can now log in.");
    }


}
