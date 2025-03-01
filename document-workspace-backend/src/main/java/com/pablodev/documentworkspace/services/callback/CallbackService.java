package com.pablodev.documentworkspace.services.callback;

import com.onlyoffice.model.documenteditor.Callback;

public interface CallbackService {
    void processCallback(Callback callback, Long documentId);
    void handlerEditing(Callback callback, Long documentId);
    void handlerSave(Callback callback, Long documentId);
    void handlerSaveCorrupted(Callback callback, Long documentId);
    void handlerClosed(Callback callback, Long documentId);
    void handlerForceSave(Callback callback, Long documentId);
    void handlerForceSaveCorrupted(Callback callback, Long documentId);
}
