package dmd.clientmanagement.util;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DotenvUtils {

    private static Dotenv dotenv;

    @Autowired
    private Dotenv dotenvBean; // Inject the Dotenv bean from ApplicationConfig

    // Initialize the static reference via PostConstruct
    @PostConstruct
    private void initDotenv() {
        DotenvUtils.dotenv = this.dotenvBean;
    }

    public static String getEnv(String key) {
        if (dotenv == null) {
            throw new IllegalStateException("Dotenv is not initialized. Ensure DotenvUtils is properly initialized by Spring.");
        }
        return dotenv.get(key);
    }
}
