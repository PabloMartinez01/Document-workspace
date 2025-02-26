package com.pablodev.documentworkspace.services.configuration;

import com.onlyoffice.model.documenteditor.Config;
import com.pablodev.documentworkspace.dto.Action;


public interface ConfigurationService {
    Config getConfiguration(Long documentId, Action action);
}
