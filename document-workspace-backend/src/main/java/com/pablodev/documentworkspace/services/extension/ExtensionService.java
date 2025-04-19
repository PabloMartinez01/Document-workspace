package com.pablodev.documentworkspace.services.extension;

import java.util.List;

public interface ExtensionService {
    List<String> findExtensionActionsByName(String name);
}
