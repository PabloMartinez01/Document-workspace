package com.pablodev.documentworkspace.managers.document;

import com.onlyoffice.model.documenteditor.config.document.DocumentType;

public interface DocumentManager {
    String getDocumentExtension(String filename);
    String getDocumentKey(Long documentId, Long documentVersion);
    DocumentType getDocumentType(String filename);
    boolean isEditable(String filename);
    boolean isViewable(String filename);
}
