package com.pablodev.documentworkspace.controllers;

import com.onlyoffice.model.documenteditor.Callback;
import com.pablodev.documentworkspace.dto.DocumentRequest;
import com.pablodev.documentworkspace.dto.DocumentResponse;
import com.pablodev.documentworkspace.services.document.DocumentService;
import lombok.Cleanup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping( "/{id}")
    public ResponseEntity<byte[]> findDocument(@PathVariable long id) throws IOException {
        DocumentResponse documentResponse = documentService.findDocument(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(documentResponse.getFilename())
                        .build()
        );

        return ResponseEntity.ok().headers(headers).body(documentResponse.getContent());
    }

    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> callbackDocument(@RequestBody Callback callback) {
        System.out.println(callback);

        Map<String, Object> response = new HashMap<>();
        response.put("error", 0);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Long> createDocument(@RequestParam MultipartFile file) throws IOException {
        String extension = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        DocumentRequest documentRequest = DocumentRequest.builder()
                .filename(file.getOriginalFilename())
                .length(file.getSize())
                .extension(extension)
                .content(file.getBytes())
                .build();

        Long id = documentService.saveDocument(documentRequest);

        return ResponseEntity.ok(id);
    }

}
