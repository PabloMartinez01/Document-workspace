package com.pablodev.documentworkspace.controllers;

import com.onlyoffice.model.documenteditor.Callback;
import com.pablodev.documentworkspace.dto.document.DocumentContent;
import com.pablodev.documentworkspace.dto.document.DocumentInfo;
import com.pablodev.documentworkspace.dto.document.DocumentRequest;
import com.pablodev.documentworkspace.services.callback.CallbackService;
import com.pablodev.documentworkspace.services.document.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final CallbackService callbackService;

    @PostMapping
    public ResponseEntity<DocumentInfo> createDocument(DocumentRequest documentRequest) throws IOException {
        DocumentInfo documentInfo = documentService.saveDocument(documentRequest);
        return ResponseEntity.ok(documentInfo);
    }

    @GetMapping( "/{id}")
    public ResponseEntity<byte[]> findDocument(@PathVariable Long id) {
        DocumentContent documentContent = documentService.findDocumentContent(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(documentContent.getFilename())
                        .build()
        );
        return ResponseEntity.ok().headers(headers).body(documentContent.getContent());
    }

    @GetMapping
    public ResponseEntity<List<DocumentInfo>> findAllDocuments() {
        List<DocumentInfo> documentsInfo = documentService.findAllDocumentInfo();
        return ResponseEntity.ok(documentsInfo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/callback/{id}")
    public ResponseEntity<Map<String, Object>> callbackDocument(@RequestBody Callback body, @PathVariable Long id) {
        try {
            callbackService.processCallback(body, id);
            return ResponseEntity.ok(Collections.singletonMap("error", 0));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("error", 1));
        }
    }



}
