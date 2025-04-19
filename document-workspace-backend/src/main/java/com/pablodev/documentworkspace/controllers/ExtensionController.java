package com.pablodev.documentworkspace.controllers;

import com.pablodev.documentworkspace.dto.extension.ExtensionResponse;
import com.pablodev.documentworkspace.services.extension.ExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/extension")
public class ExtensionController {

    private final ExtensionService extensionService;

    @GetMapping("{extension}")
    public ResponseEntity<ExtensionResponse> findExtension(@PathVariable("extension") String extension) {
        return ResponseEntity.ok(extensionService.findExtensionByName(extension));
    }


}
