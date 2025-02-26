package com.pablodev.documentworkspace.controllers;

import com.onlyoffice.model.documenteditor.Config;
import com.pablodev.documentworkspace.dto.Action;
import com.pablodev.documentworkspace.services.configuration.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/configuration")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @GetMapping("/{id}")
    public Config getConfiguration(@PathVariable Long id, @RequestParam(defaultValue = "view") Action action) {
        return configurationService.getConfiguration(id, action);
    }

}
