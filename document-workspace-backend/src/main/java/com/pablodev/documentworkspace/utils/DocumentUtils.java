package com.pablodev.documentworkspace.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class DocumentUtils {

    public String getExtensionFromMultipart(MultipartFile multipartFile) {
        return getExtensionFromFilename(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    }

    public String getExtensionFromFilename(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1))
                .map(String::toLowerCase)
                .filter(Predicate.not(String::isEmpty))
                .orElseThrow(() -> new RuntimeException("Invalid filename format"));
    }

}
