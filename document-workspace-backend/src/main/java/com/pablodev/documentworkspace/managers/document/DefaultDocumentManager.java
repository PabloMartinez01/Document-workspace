package com.pablodev.documentworkspace.managers.document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyoffice.model.common.Format;
import com.onlyoffice.model.documenteditor.config.document.DocumentType;
import com.pablodev.documentworkspace.utils.DocumentUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultDocumentManager implements DocumentManager  {

    private final DocumentUtils documentUtils;
    private List<Format> formats;

    @PostConstruct
    public void postConstruct() throws IOException {
        ClassPathResource resource = new ClassPathResource("assets/document-formats.json");
        this.formats = new ObjectMapper()
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .readValue(resource.getInputStream(),new TypeReference<>() {});
    }


    @Override
    public String getDocumentKey(Long documentId, Long documentVersion) {
        try {
            byte[] hashBytes = MessageDigest.getInstance("SHA-256")
                    .digest(String.valueOf(documentId)
                            .concat("_")
                            .concat(String.valueOf(documentVersion))
                            .getBytes(StandardCharsets.UTF_8));
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
        String documentExtension = documentUtils.getExtensionFromFilename(filename);
        if (documentExtension != null) {
            List<DocumentType> types = this.formats.stream()
                    .filter(format -> format.getName().equals(documentExtension))
                    .map(Format::getType)
                    .toList();
            if (!types.isEmpty()) return types.getFirst();
        }
        return null;
    }


}
