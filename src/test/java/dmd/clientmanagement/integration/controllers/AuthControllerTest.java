package dmd.clientmanagement.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmd.clientmanagement.config.SecurityConfig;
import dmd.clientmanagement.controllers.AuthController;
import dmd.clientmanagement.dto.AuthResponse;
import dmd.clientmanagement.dto.RegisterRequest;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.security.JwtService;
import dmd.clientmanagement.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testRegisterUser() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("andresfg123");
        registerRequest.setPassword("123");
        registerRequest.setFirstname("andres");
        registerRequest.setLastname("gonza");
        registerRequest.setCountry("colombia");

        AuthResponse mockAuthResponse = new AuthResponse();
        mockAuthResponse.setToken("mock-token");
        mockAuthResponse.setUsername("andresfg123");
        mockAuthResponse.setRole("USER");
        mockAuthResponse.setUserId(1L);

        when(authService.register(any(RegisterRequest.class)))
                .thenReturn(mockAuthResponse);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest))
                        .with(csrf()))  // Disable CSRF protection for this request
                .andExpect(status().isOk());
    }
}

