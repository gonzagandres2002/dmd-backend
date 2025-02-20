package dmd.clientmanagement.service;

import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.entity.user.Role;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.repository.ServiceTypeRepository;
import dmd.clientmanagement.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitializationService {

    private final UserRepository userRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;

    @PostConstruct
    public void init() {
        createServiceTypeIfNotExists("software");
        createServiceTypeIfNotExists("marketing");
        createAdminUserIfNotExists();
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
        if (!userRepository.existsByUsername(adminUsername)) {
            User adminUser = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .email(adminEmail)
                    .role(Role.ADMIN)
                    .emailVerified(true)
                    .build();
            userRepository.save(adminUser);
        }
    }
}
