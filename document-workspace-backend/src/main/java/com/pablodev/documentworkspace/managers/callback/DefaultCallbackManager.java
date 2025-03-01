package com.pablodev.documentworkspace.managers.callback;

import com.onlyoffice.model.documenteditor.Callback;
import com.onlyoffice.model.documenteditor.callback.Action;
import com.onlyoffice.model.documenteditor.callback.action.Type;
import com.pablodev.documentworkspace.services.document.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class DefaultCallbackManager implements CallbackManager {

    private final DocumentService documentService;

    @Override
    public void processEditing(Callback callback, Long documentId) {
        log.info("Document open");
        Action action = callback.getActions().get(0);
        if (action.getType().equals(Type.CONNECTED)) {
            documentService.updateDocumentOpenStatus(documentId, true);
        }
    }

    @Override
    public void processSave(Callback callback, Long documentId) {
        log.info("Document saved");
        documentService.updateDocumentOpenStatus(documentId, false);
    }

    @Override
    public void processForceSave(Callback callback, Long documentId) {
        log.info("Document force saved");
    }

    @Override
    public void processClose(Callback callback, Long documentId) {
        log.info("Document closed");
        documentService.updateDocumentOpenStatus(documentId, false);
    }
}
