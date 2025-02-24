package com.pablodev.documentworkspace.managers.document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyoffice.model.common.Format;
import com.onlyoffice.model.documenteditor.config.document.DocumentType;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class DefaultDocumentManager implements DocumentManager  {

    private List<Format> formats;

    @PostConstruct
    public void postConstruct() throws IOException {
        ClassPathResource resource = new ClassPathResource("assets/document-formats.json");
        this.formats = new ObjectMapper()
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .readValue(resource.getInputStream(),new TypeReference<>() {});
    }

    @Override
    public String getDocumentExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1))
                .map(String::toLowerCase)
                .filter(Predicate.not(String::isEmpty))
                .map(String::toLowerCase)
                .orElse(null);
    }

    @Override
    public String getDocumentKey(Long documentId) {
        try {
            byte[] hashBytes = MessageDigest.getInstance("SHA-256")
                    .digest(String.valueOf(documentId).getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(hashBytes)
                    .replace("[^0-9-.a-zA-Z_=]", "_");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DocumentType getDocumentType(String filename) {
        return Optional.ofNullable(getDocumentExtension(filename))
                .flatMap(ext -> formats.stream()
                        .filter(format -> format.getName().equals(ext))
                        .map(Format::getType)
                        .findFirst())
                .orElse(null);
    }

    @Override
    public boolean isEditable(String filename) {
       return Optional.ofNullable(getDocumentExtension(filename))
               .flatMap(ext -> formats.stream()
                       .filter(format -> format.getName().equals(ext))
                       .map(format -> format.getActions().contains("edit"))
                       .findFirst())
               .orElse(false);
    }


    @Override
    public boolean isViewable(String filename) {
        return Optional.ofNullable(getDocumentExtension(filename))
                .flatMap(ext -> formats.stream()
                        .filter(format -> format.getName().equals(ext))
                        .map(format -> format.getActions().contains("view"))
                        .findFirst())
                .orElse(false);
    }
}
