package com.pablodev.documentworkspace.controllers;

import com.pablodev.documentworkspace.dto.document.DocumentRequest;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;
import com.pablodev.documentworkspace.services.folder.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @GetMapping("/{id}")
    public ResponseEntity<FolderResponse> getFolder(@PathVariable Long id) {
        return ResponseEntity.ok(folderService.findFolderById(id));
    }

    @PostMapping
    public ResponseEntity<FolderResponse> createFolder(@RequestBody FolderRequest folderRequest) {
        return ResponseEntity.ok(folderService.createFolder(folderRequest));
    }

    @PostMapping("/{id}")
    public ResponseEntity<FolderResponse> createDocumentInFolder(
            @PathVariable Long id,
            @RequestParam MultipartFile file
    ) throws IOException {

        String extension = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        DocumentRequest documentRequest = DocumentRequest.builder()
                .filename(file.getOriginalFilename())
                .length(file.getSize())
                .extension(extension)
                .content(file.getBytes())
                .build();

        FolderResponse folderResponse = folderService.saveDocumentInFolder(id, documentRequest);
        return ResponseEntity.ok(folderResponse);
    }

}
