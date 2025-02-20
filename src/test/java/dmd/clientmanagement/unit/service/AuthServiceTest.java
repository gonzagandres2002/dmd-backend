package dmd.clientmanagement.unit.service;

import dmd.clientmanagement.dto.AuthResponse;
import dmd.clientmanagement.dto.LoginRequest;
import dmd.clientmanagement.dto.RegisterRequest;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.security.JwtService;
import dmd.clientmanagement.service.AuthService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testLogin() {
        // Given
        LoginRequest request = new LoginRequest("username", "password");
        User user = User.builder()
                .id(1L)
                .username("username")
                .password("encodedPassword")
                .firstname("firstname")
                .lastname("lastname")
                .country("country")
                .role(Role.USER)
                .emailVerified(true)
                .build();

        // When
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(jwtService.getToken(user)).thenReturn("token");

        AuthResponse response = authService.login(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("token");
        assertThat(response.getUsername()).isEqualTo("username");
        assertThat(response.getRole()).isEqualTo("USER");
        assertThat(response.getUserId()).isEqualTo(1L);
    }

    @Test
    public void testRegister() {
        // Given
        RegisterRequest request = new RegisterRequest("username", "password", "email", "firstname", "lastname", "country");
        User user = User.builder()
                .id(1L)
                .username("username")
                .password("encodedPassword")
                .email("email")
                .firstname("firstname")
                .lastname("lastname")
                .country("country")
                .role(Role.USER)
                .emailVerified(false)
                .build();

        MimeMessage mimeMessage = org.mockito.Mockito.mock(MimeMessage.class);

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.getToken(any(User.class))).thenReturn("dummy-token");
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        AuthResponse response = authService.register(request);

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotNull();
    }
}