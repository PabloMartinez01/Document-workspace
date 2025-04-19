package com.pablodev.documentworkspace.services.document;

import com.pablodev.documentworkspace.dto.document.DocumentContentResponse;
import com.pablodev.documentworkspace.dto.document.DocumentFilterRequest;
import com.pablodev.documentworkspace.dto.document.DocumentRequest;
import com.pablodev.documentworkspace.dto.document.DocumentResponse;

import java.io.IOException;
import java.util.List;

public interface DocumentService {
    DocumentResponse saveDocument(DocumentRequest documentRequest) throws IOException;
    DocumentContentResponse findDocumentContent(Long documentId);
    DocumentResponse findDocumentInfo(Long documentId);
    List<DocumentResponse> findAllDocumentInfo();
    void updateDocumentLock(Long documentId, boolean locked);
    void updateDocumentContent(Long documentId, byte[] content, boolean lock);
    void deleteDocument(Long documentId);
    List<DocumentResponse> findDocumentsByFilters(DocumentFilterRequest documentFilterRequest);
}
