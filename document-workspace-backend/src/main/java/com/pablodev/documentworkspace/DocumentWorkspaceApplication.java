package com.pablodev.documentworkspace;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties
@SpringBootApplication
public class DocumentWorkspaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentWorkspaceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println(passwordEncoder.encode("password"));
        };
    }

}
