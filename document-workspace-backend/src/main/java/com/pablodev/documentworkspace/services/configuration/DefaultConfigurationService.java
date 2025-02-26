package com.pablodev.documentworkspace.services.configuration;

import com.onlyoffice.model.documenteditor.Config;
import com.onlyoffice.model.documenteditor.config.Document;
import com.onlyoffice.model.documenteditor.config.EditorConfig;
import com.onlyoffice.model.documenteditor.config.editorconfig.Mode;
import com.pablodev.documentworkspace.dto.Action;
import com.pablodev.documentworkspace.dto.DocumentInfo;
import com.pablodev.documentworkspace.managers.document.DocumentManager;
import com.pablodev.documentworkspace.managers.url.UrlManager;
import com.pablodev.documentworkspace.services.document.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultConfigurationService implements ConfigurationService {

    private final DocumentService documentService;
    private final DocumentManager documentManager;
    private final UrlManager urlManager;

    @Override
    public Config getConfiguration(Long documentId, Action action) {

        DocumentInfo documentInfo = documentService.findDocumentInfo(documentId);

        boolean isEditable = documentManager.isEditable(documentInfo.getFilename());
        boolean isViewable = documentManager.isViewable(documentInfo.getFilename());

        if (!isEditable && !isViewable) {
            throw new RuntimeException("The specified format is not supported");
        }

        Mode mode = (action.equals(Action.edit) && isEditable) ? Mode.EDIT : Mode.VIEW;

        Config config = Config.builder()
                .document(Document.builder()
                        .key(documentManager.getDocumentKey(documentId))
                        .fileType(documentInfo.getExtension())
                        .title(documentInfo.getFilename())
                        .url(urlManager.getDocumentUrl(documentId))
                        .build()
                )
                .documentType(documentManager.getDocumentType(documentInfo.getFilename()))
                .editorConfig(EditorConfig.builder()
                        .mode(mode)
                        .callbackUrl(urlManager.getDocumentCallback())
                        .build()
                )
                .build();

        return config;
    }

}
