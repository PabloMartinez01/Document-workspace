package com.pablodev.documentworkspace.services.configuration;

import com.onlyoffice.model.documenteditor.Config;
import com.onlyoffice.model.documenteditor.config.Document;
import com.onlyoffice.model.documenteditor.config.EditorConfig;
import com.onlyoffice.model.documenteditor.config.document.DocumentType;
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
    public Config getConfiguration(Long documentId) {

        DocumentInfo documentInfo = documentService.findDocumentInfo(documentId);

        Config config = Config.builder()
                .document(Document.builder()
                        .key(documentManager.getDocumentKey(documentId))
                        .fileType(documentInfo.getExtension())
                        .title(documentInfo.getFilename())
                        .url(urlManager.getDocumentUrl(documentId))
                        .build()
                )
                .documentType(DocumentType.WORD)
                .editorConfig(EditorConfig.builder()
                        .callbackUrl(urlManager.getDocumentCallback())
                        .build()
                )
                .build();

        return config;
    }

}
