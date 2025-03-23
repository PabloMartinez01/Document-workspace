package com.pablodev.documentworkspace.controllers;

import com.pablodev.documentworkspace.dto.folder.FolderInfo;
import com.pablodev.documentworkspace.dto.folder.FolderItemsResponse;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;
import com.pablodev.documentworkspace.services.folder.FolderService;
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

    @GetMapping(value = "/{id}", params = "name")
    public ResponseEntity<FolderItemsResponse> findFolderItemsByName(@PathVariable Long id, @RequestParam String name) {
        return ResponseEntity.ok(folderService.findFolderItemsByName(id, name));
    }

    @PostMapping
    public ResponseEntity<FolderInfo> createFolder(@RequestBody FolderRequest folderRequest) {
        return ResponseEntity.ok(folderService.saveFolder(folderRequest));
    }

}
