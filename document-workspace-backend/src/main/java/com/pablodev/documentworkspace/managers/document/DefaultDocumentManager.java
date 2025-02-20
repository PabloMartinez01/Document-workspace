package com.pablodev.documentworkspace.managers.document;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class DefaultDocumentManager implements DocumentManager  {

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

}
