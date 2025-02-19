package com.pablodev.documentworkspace.controllers;

import com.onlyoffice.model.documenteditor.Config;
import com.pablodev.documentworkspace.services.configuration.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/configuration")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @GetMapping("/{id}")
    public Config getConfiguration(@PathVariable Long id) {
        return configurationService.getConfiguration(id);
    }

}
