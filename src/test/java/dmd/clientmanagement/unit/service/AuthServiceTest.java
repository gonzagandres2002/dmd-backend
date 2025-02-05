package dmd.clientmanagement.unit.service;

import dmd.clientmanagement.dto.AuthResponse;
import dmd.clientmanagement.dto.LoginRequest;
import dmd.clientmanagement.dto.RegisterRequest;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.security.JwtService;
import dmd.clientmanagement.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
        authService = new AuthService(userRepository, jwtService, passwordEncoder, authenticationManager);
    }

    @Test
    public void testLogin() {
        // Given
        LoginRequest request = new LoginRequest("username", "password");
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setCountry("country");
        user.setRole(Role.USER);

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
        RegisterRequest request = new RegisterRequest("username", "password", "firstname", "lastname", "country");
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setCountry("country");
        user.setRole(Role.USER);

        // When
        AuthResponse response = authService.register(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isNotNull();
    }
}
