package org.example.modul223backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Modul223BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Modul223BackendApplication.class, args);
    }

    @GetMapping("/")
    public String index(){return "index";}
}
