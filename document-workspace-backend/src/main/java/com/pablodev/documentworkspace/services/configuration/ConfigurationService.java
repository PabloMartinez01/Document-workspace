package com.pablodev.documentworkspace.services.configuration;

import com.onlyoffice.model.documenteditor.Config;

public interface ConfigurationService {
    Config getConfiguration(Long documentId);
}
