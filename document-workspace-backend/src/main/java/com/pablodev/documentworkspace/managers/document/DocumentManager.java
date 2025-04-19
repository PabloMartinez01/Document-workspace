package com.pablodev.documentworkspace.managers.document;

import com.onlyoffice.model.documenteditor.config.document.DocumentType;

public interface DocumentManager {
    String getDocumentKey(Long documentId, Long documentVersion);
    DocumentType getDocumentType(String filename);
}
