package dmd.clientmanagement.config;

import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.repository.ServiceTypeRepository;
import dmd.clientmanagement.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    @Bean
    public Dotenv dotenv() {
        return Dotenv.load(); // Load the .env file
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    @PostConstruct
    public void init() {
        createServiceTypeIfNotExists("software");
        createServiceTypeIfNotExists("marketing");
    }

    private void createServiceTypeIfNotExists(String serviceName) {
        if (!serviceTypeRepository.existsByName(serviceName)) {
            ServiceType serviceType = ServiceType.builder()
                    .name(serviceName)
                    .build();
            serviceTypeRepository.save(serviceType);
        }
    }

    private void createAdminUserIfNotExists() {
        String adminUsername = dotenv().get("ADMIN_USERNAME");
        String adminPassword = dotenv().get("ADMIN_PASSWORD");
        String adminEmail = dotenv().get("ADMIN_EMAIL");

        if (!userRepository.existsByUsername(adminUsername)) {
            User adminUser = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder().encode(adminPassword))
                    .email(adminEmail)
                    .role(Role.ADMIN)
                    .emailVerified(true)
                    .build();
            userRepository.save(adminUser);
        }
    }

}
