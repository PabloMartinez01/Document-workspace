package com.pablodev.documentworkspace.managers.callback;

import com.onlyoffice.model.documenteditor.Callback;
import com.onlyoffice.model.documenteditor.callback.Action;
import com.onlyoffice.model.documenteditor.callback.action.Type;
import com.pablodev.documentworkspace.services.document.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Component
@RequiredArgsConstructor
public class DefaultCallbackManager implements CallbackManager {

    private final DocumentService documentService;
    private final RestTemplate restTemplate;

    @Override
    public void processEditing(Callback callback, Long documentId) {
        log.info("Document open");
        Action action = callback.getActions().get(0);
        if (action.getType().equals(Type.CONNECTED)) {
            documentService.updateDocumentLock(documentId, true);
        }
    }

    @Override
    public void processSave(Callback callback, Long documentId) {
        log.info("Saving document");
        byte[] bytes = downloadDocument(callback.getUrl());
        documentService.updateDocumentContent(documentId, bytes, false);
        log.info("Document saved");
    }

    @Override
    public void processForceSave(Callback callback, Long documentId) {
        log.info("Force saving document");
        byte[] bytes = downloadDocument(callback.getUrl());
        documentService.updateDocumentContent(documentId, bytes, true);
        log.info("Document force saved");
    }

    @Override
    public void processClose(Callback callback, Long documentId) {
        log.info("Document closed");
        documentService.updateDocumentLock(documentId, false);
    }

    private byte[] downloadDocument(String documentUrl) {
        ResponseEntity<byte[]> response = restTemplate.getForEntity(documentUrl, byte[].class);
        return response.getBody();
    }

}
