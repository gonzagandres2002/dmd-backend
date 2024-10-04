package dmd.clientmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DmdBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DmdBackendApplication.class, args);
    }

}
