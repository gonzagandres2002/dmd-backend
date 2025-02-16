package dmd.clientmanagement;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DmdBackendApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        System.setProperty("GMAIL_MAIL_USERNAME", dotenv.get("GMAIL_MAIL_USERNAME"));
        System.setProperty("GMAIL_MAIL_PASSWORD", dotenv.get("GMAIL_MAIL_PASSWORD"));


        SpringApplication.run(DmdBackendApplication.class, args);
    }

}
