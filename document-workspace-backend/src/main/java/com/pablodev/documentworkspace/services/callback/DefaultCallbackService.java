package com.pablodev.documentworkspace.services.callback;

import com.onlyoffice.model.documenteditor.Callback;
import com.onlyoffice.model.documenteditor.callback.Status;
import com.pablodev.documentworkspace.managers.callback.CallbackManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class DefaultCallbackService implements CallbackService {

    private final CallbackManager callbackManager;
    private Map<Status, BiConsumer<Callback, Long>> handlers;

    @PostConstruct
    public void postConstruct() {
        handlers = new EnumMap<>(Status.class);
        handlers.put(Status.EDITING, this::handlerEditing);
        handlers.put(Status.SAVE, this::handlerSave);
        handlers.put(Status.SAVE_CORRUPTED, this::handlerSaveCorrupted);
        handlers.put(Status.CLOSED, this::handlerClosed);
        handlers.put(Status.FORCESAVE, this::handlerForceSave);
        handlers.put(Status.FORCESAVE_CORRUPTED, this::handlerForceSaveCorrupted);
    }

    @Override
    public void processCallback(Callback callback, Long documentId) {
        handlers.get(callback.getStatus()).accept(callback, documentId);
    }

    @Override
    public void handlerEditing(Callback callback, Long documentId) {
        callbackManager.processEditing(callback, documentId);
    }

    @Override
    public void handlerSave(Callback callback, Long documentId) {
        callbackManager.processSave(callback, documentId);
    }

    @Override
    public void handlerSaveCorrupted(Callback callback, Long documentId) {
        callbackManager.processSave(callback, documentId);
    }

    @Override
    public void handlerClosed(Callback callback, Long documentId) {
        callbackManager.processClose(callback, documentId);
    }

    @Override
    public void handlerForceSave(Callback callback, Long documentId) {
        callbackManager.processForceSave(callback, documentId);
    }

    @Override
    public void handlerForceSaveCorrupted(Callback callback, Long documentId) {

    }
}
