package com.pablodev.documentworkspace.services.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyoffice.model.documenteditor.Config;
import com.onlyoffice.model.documenteditor.config.Document;
import com.onlyoffice.model.documenteditor.config.EditorConfig;
import com.onlyoffice.model.documenteditor.config.editorconfig.Customization;
import com.onlyoffice.model.documenteditor.config.editorconfig.Mode;
import com.pablodev.documentworkspace.dto.Action;
import com.pablodev.documentworkspace.dto.document.DocumentResponse;
import com.pablodev.documentworkspace.managers.document.DocumentManager;
import com.pablodev.documentworkspace.managers.url.UrlManager;
import com.pablodev.documentworkspace.mappers.DocumentTypeMapper;
import com.pablodev.documentworkspace.model.User;
import com.pablodev.documentworkspace.services.document.DocumentService;
import com.pablodev.documentworkspace.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultConfigurationService implements ConfigurationService {

    private final DocumentService documentService;
    private final DocumentManager documentManager;
    private final DocumentTypeMapper documentTypeMapper;
    private final UrlManager urlManager;
    private final JwtService jwtService;

    @Override
    public Config getConfiguration(Long documentId, Action action, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        DocumentResponse documentResponse = documentService.findDocumentInfo(documentId);
        List<String> actions = documentResponse.getExtension().getActions();

        boolean isEditable = actions.contains("edit");
        boolean isViewable = actions.contains("view");

        if (!isEditable && !isViewable) {
            throw new RuntimeException("The specified format is not supported");
        }

        Mode mode = (action.equals(Action.edit) && isEditable) ? Mode.EDIT : Mode.VIEW;

        Config config = Config.builder()
                .document(Document.builder()
                        .key(documentManager.getDocumentKey(documentId, documentResponse.getVersion()))
                        .fileType(documentResponse.getExtension().getName())
                        .title(documentResponse.getFilename())
                        .url(urlManager.getDocumentUrl(documentId))
                        .build()
                )
                .documentType(documentTypeMapper.toDocumentType(documentResponse.getExtension().getType()))
                .editorConfig(EditorConfig.builder()
                        .mode(mode)
                        .callbackUrl(urlManager.getDocumentCallback(documentId))
                        .customization(Customization.builder()
                                .forcesave(true)
                                .build())
                        .user(com.onlyoffice.model.common.User.builder()
                                .id(String.valueOf(user.getId()))
                                .name(user.getName())
                                .build())
                        .build()
                )
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> configMap = objectMapper.convertValue(config, new TypeReference<>() {});

        String token = jwtService.createToken(configMap, user);
        config.setToken(token);

        return config;
    }

}
