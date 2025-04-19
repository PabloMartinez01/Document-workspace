package com.pablodev.documentworkspace.initializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablodev.documentworkspace.model.Extension;
import com.pablodev.documentworkspace.model.Type;
import com.pablodev.documentworkspace.repositories.ExtensionRepository;
import com.pablodev.documentworkspace.repositories.TypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2)
public class ExtensionInitializer implements CommandLineRunner {

    @Data
    public static class ExtensionData {
        private String name;
        private String type;
        private List<String>  actions;
        private List<String> convert;
        private List<String> mime;
    }

    private final ExtensionRepository extensionRepository;
    private final TypeRepository typeRepository;

    private List<ExtensionData> extensionsData;

    @PostConstruct
    public void postConstruct() throws IOException {
        ClassPathResource resource = new ClassPathResource("assets/document-formats.json");
        this.extensionsData = new ObjectMapper()
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .readValue(resource.getInputStream(),new TypeReference<>() {});
    }

    @Override
    @Transactional
    public void run(String... args) {
        Type defaultType = typeRepository.findDefault();
        extensionsData.forEach(extensionData -> {
            if (extensionRepository.findByName(extensionData.getName()).isEmpty()) {
                Type type = typeRepository.findByName(extensionData.getType()).orElse(defaultType);
                extensionRepository.save(new Extension(extensionData.getName(), type, extensionData.getActions()));
            }
        });
    }

}
