package dmd.clientmanagement.service;

import dmd.clientmanagement.dto.AuthResponse;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.repository.UserRepository;
import dmd.clientmanagement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthResponse processOAuth2User(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Find or create the user
        User user = userRepository.findByUsername(email).orElseGet(() -> {
            User newUser = User.builder()
                    .username(email)
                    .firstname(name.split(" ")[0])
                    .lastname(name.split(" ")[1])
                    .role(Role.USER)
                    .build();
            return userRepository.save(newUser);
        });

        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
