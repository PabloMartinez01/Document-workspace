package com.pablodev.documentworkspace.mappers;

import com.pablodev.documentworkspace.dto.extension.ExtensionResponse;
import com.pablodev.documentworkspace.model.Extension;
import org.springframework.stereotype.Component;

@Component
public class ExtensionMapper {

    public ExtensionResponse toExtensionResponse(Extension extension) {
        return ExtensionResponse.builder()
                .name(extension.getName())
                .type(extension.getType().getName())
                .actions(extension.getActions())
                .build();
    }
    
}
