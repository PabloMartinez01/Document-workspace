package com.pablodev.documentworkspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DocumentWorkspaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentWorkspaceApplication.class, args);
    }

}
