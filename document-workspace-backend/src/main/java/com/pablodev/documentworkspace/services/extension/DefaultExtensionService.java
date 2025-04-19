package com.pablodev.documentworkspace.services.extension;

import com.pablodev.documentworkspace.model.Extension;
import com.pablodev.documentworkspace.repositories.ExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultExtensionService implements ExtensionService {

    private final ExtensionRepository extensionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<String> findExtensionActionsByName(String name) {
        Extension extension = extensionRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Extension not found: " + name));
        return  extension.getActions();
    }
}
