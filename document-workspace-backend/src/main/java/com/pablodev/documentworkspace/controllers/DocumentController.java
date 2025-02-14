package com.pablodev.documentworkspace.controllers;

import com.pablodev.documentworkspace.dto.DocumentResponse;
import com.pablodev.documentworkspace.services.document.DocumentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("{id}")
    public ResponseEntity<byte[]> findDocument(@PathVariable long id) {
        DocumentResponse documentResponse = documentService.findDocument(id);
        return ResponseEntity.ok(documentResponse.getContent());
    }

}
