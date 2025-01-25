package org.example.modul223backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main application class for the Modul223 backend.
 * Handles application startup and configuration loading.
 */
@RestController
@SpringBootApplication
public class Modul223BackendApplication {

    /**
     * The main entry point of the application.
     * Loads environment variables, sets system properties, and starts the Spring Boot application.
     * @param args command-line arguments, (when given :).
     */
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        SpringApplication.run(Modul223BackendApplication.class, args);
    }

    @GetMapping("/")
    public String index(){return "index";}
}
