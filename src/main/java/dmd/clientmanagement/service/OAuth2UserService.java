package dmd.clientmanagement.service;

import dmd.clientmanagement.dto.AuthResponse;
import dmd.clientmanagement.dto.OAuth2TokenRequest;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.mapper.OAuth2UserDetails;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.security.JwtService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService {

    private final JwtService jwtService;
    private final Dotenv dotenv;
    private final WebClient webClient;
    private final UserRepository userRepository;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    @Value("${google.token-uri}")
    private String tokenUri;

    @Value("${google.user-info-uri}")
    private String userInfoUri;

    /**
     * Validates the token with Google and retrieves the user info.
     *
     * @param tokenRequest Contains the authorization code.
     * @return An OAuth2User with user information from Google.
     */
    public OAuth2User validateTokenWithGoogle(OAuth2TokenRequest tokenRequest) {
        // Step 1: Exchange the authorization code for an access token
        Map<String, String> tokenRequestBody = Map.of(
                "code", tokenRequest.getCode(),
                "client_id", dotenv.get("GOOGLE_CLIENT_ID"),
                "client_secret", dotenv.get("GOOGLE_CLIENT_SECRET"),
                "redirect_uri", redirectUri,
                "grant_type", "authorization_code"
        );

        Map<String, Object> tokenResponse = webClient.post()
                .uri(tokenUri)
                .bodyValue(tokenRequestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (tokenResponse == null || !tokenResponse.containsKey("access_token")) {
            throw new IllegalStateException("Failed to retrieve access token from Google");
        }

        String accessToken = (String) tokenResponse.get("access_token");

        // Step 2: Retrieve user information using the access token
        Map<String, Object> userInfo = webClient.get()
                .uri(userInfoUri)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (userInfo == null || !userInfo.containsKey("email")) {
            throw new IllegalStateException("Failed to retrieve user info from Google");
        }

        // Step 3: Map user information to an OAuth2User
        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("USER")), // Default authority
                userInfo,
                "email" // The key in Google's user info for the principal name
        );
    }

    /**
     * Processes the OAuth2 user, creates or fetches a user in the database, and generates a JWT.
     *
     * @param oAuth2User The authenticated user.
     * @return AuthResponse containing the JWT, user details, and userId.
     */
    public AuthResponse processOAuth2User(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        // Check if the user exists in the database
        User user = userRepository.findByUsername(email).orElseGet(() -> {
            // Create a new user if not found
            User newUser = User.builder()
                    .username(email) // Use email as the username
                    .role(Role.USER) // Default role for OAuth2 users
                    .build();
            return userRepository.save(newUser); // Save the new user to the database
        });

        // Generate a token using the user
        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .userId(user.getId()) // Include the userId
                .build();
    }

}

