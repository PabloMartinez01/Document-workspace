package com.pablodev.documentworkspace.managers.callback;

import com.onlyoffice.model.documenteditor.Callback;

public interface CallbackManager {
    void processEditing(Callback callback, Long documentId);
    void processSave(Callback callback, Long documentId);
    void processForceSave(Callback callback, Long documentId);
    void processClose(Callback callback, Long documentId);
}
