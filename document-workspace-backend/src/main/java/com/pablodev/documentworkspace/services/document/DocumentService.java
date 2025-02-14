package com.pablodev.documentworkspace.services.document;

import com.pablodev.documentworkspace.dto.DocumentRequest;
import com.pablodev.documentworkspace.dto.DocumentResponse;

public interface DocumentService {
    Long saveDocument(DocumentRequest documentRequest);
    DocumentResponse findDocument(Long id);
    void deleteDocument(Long id);
}
