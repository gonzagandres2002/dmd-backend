package dmd.clientmanagement.service;

import dmd.clientmanagement.dto.AuthResponse;
import dmd.clientmanagement.dto.OAuth2TokenRequest;
import dmd.clientmanagement.mapper.OAuth2UserDetails;
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
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService {

    private final JwtService jwtService;
    private final Dotenv dotenv;
    private final RestTemplate restTemplate;

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

        Map<String, Object> tokenResponse = restTemplate.postForObject(
                tokenUri,
                tokenRequestBody,
                Map.class
        );

        if (tokenResponse == null || !tokenResponse.containsKey("access_token")) {
            throw new IllegalStateException("Failed to retrieve access token from Google");
        }

        String accessToken = (String) tokenResponse.get("access_token");

        // Step 2: Retrieve user information using the access token
        Map<String, Object> userInfo = restTemplate.getForObject(
                userInfoUri + "?access_token=" + accessToken,
                Map.class
        );

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
     * Processes the OAuth2 user and generates a JWT.
     *
     * @param oAuth2User The authenticated user.
     * @return AuthResponse containing the JWT and user details.
     */
    public AuthResponse processOAuth2User(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = new OAuth2UserDetails(email, authorities);

        // Generate a token using UserDetails
        String token = jwtService.getToken(userDetails);

        return new AuthResponse(token, email, "USER"); // Default role assigned
    }
}

