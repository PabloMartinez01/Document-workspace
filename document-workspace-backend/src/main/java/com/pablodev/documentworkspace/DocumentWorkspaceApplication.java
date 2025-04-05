package com.pablodev.documentworkspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class DocumentWorkspaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentWorkspaceApplication.class, args);
    }

}
