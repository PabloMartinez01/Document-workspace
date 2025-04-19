package com.pablodev.documentworkspace.services.extension;

import com.pablodev.documentworkspace.dto.extension.ExtensionResponse;

public interface ExtensionService {
    ExtensionResponse findExtensionByName(String name);
}
