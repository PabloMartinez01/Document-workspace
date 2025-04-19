package com.pablodev.documentworkspace.services.extension;

import com.pablodev.documentworkspace.dto.extension.ExtensionResponse;
import com.pablodev.documentworkspace.mappers.ExtensionMapper;
import com.pablodev.documentworkspace.model.Extension;
import com.pablodev.documentworkspace.repositories.ExtensionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultExtensionService implements ExtensionService {

    private final ExtensionRepository extensionRepository;
    private final ExtensionMapper extensionMapper;

    @Override
    @Transactional(readOnly = true)
    public ExtensionResponse findExtensionByName(String name) {
        Extension extension = extensionRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Extension not found: " + name));
        return extensionMapper.toExtensionResponse(extension);
    }

}
