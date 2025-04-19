package com.pablodev.documentworkspace.controllers;

import com.pablodev.documentworkspace.dto.folder.FolderInfoResponse;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;
import com.pablodev.documentworkspace.services.folder.FolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @GetMapping("/{id}")
    public ResponseEntity<FolderResponse> findFolder(@PathVariable Long id) {
        return ResponseEntity.ok(folderService.findFolderById(id));
    }

    @PostMapping
    public ResponseEntity<FolderInfoResponse> createFolder(@RequestBody @Valid FolderRequest folderRequest) {
        return ResponseEntity.ok(folderService.saveFolder(folderRequest));
    }

}
