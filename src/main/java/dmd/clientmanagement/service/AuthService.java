package dmd.clientmanagement.service;

import dmd.clientmanagement.dto.LoginRequest;
import dmd.clientmanagement.dto.RegisterRequest;
import dmd.clientmanagement.dto.AuthResponse;
import dmd.clientmanagement.security.JwtService;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender mailSender;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check if the user is active
        if (!user.isEmailVerified()) {
            throw new IllegalStateException("Please verify your email address before logging in.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getAuthorities().stream().findFirst().get().getAuthority())
                .userId(user.getId())
                .build();
    }



    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .role(Role.USER)
                .emailVerified(false)
                .build();

        // Save the user and capture the returned entity with the generated id.
        User savedUser = userRepository.save(user);

        String token = jwtService.getToken(savedUser);

        sendVerificationEmail(savedUser.getEmail(), token);

        return AuthResponse.builder()
                .username(savedUser.getUsername())
                .message("Registration successful! Please verify your email to activate your account.")
                .build();
    }

    private void sendVerificationEmail(String email, String token) {
        String subject = "Verify Your Email Address";
        String verificationLink = "http://localhost:8080/auth/verify?token=" + token;

        String emailContent = "<p>Dear user,</p>" +
                "<p>Click the link below to verify your email address:</p>" +
                "<a href=\"" + verificationLink + "\">Verify Email</a>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message); 
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(emailContent, true); // True for HTML content

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email", e);
        }
    }

}
