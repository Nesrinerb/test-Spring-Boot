package com.rabianesrine.testspringboot;

import com.rabianesrine.testspringboot.auth.AuthService;
import com.rabianesrine.testspringboot.auth.RegisterRequest;
import com.rabianesrine.testspringboot.entities.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TestSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthService service
    ){
        return args -> {
            var client = RegisterRequest.builder()
                    .nom("test")
                    .prenom("test")
                    .email("123")
                    .motsDePasse("123")
                    .role(Role.CLIENT)
                    .build();
            System.out.println("Test token: " + service.register(client).getAccessToken());
        };

    }

}
