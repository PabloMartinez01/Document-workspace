package com.pablodev.documentworkspace.services.document;

import com.pablodev.documentworkspace.dto.document.DocumentContent;
import com.pablodev.documentworkspace.dto.document.DocumentInfo;
import com.pablodev.documentworkspace.dto.document.DocumentRequest;

import java.io.IOException;
import java.util.List;

public interface DocumentService {
    DocumentInfo saveDocument(DocumentRequest documentRequest) throws IOException;
    DocumentContent findDocumentContent(Long documentId);
    DocumentInfo findDocumentInfo(Long documentId);
    List<DocumentInfo> findAllDocumentInfo();
    void updateDocumentLock(Long documentId, boolean locked);
    void updateDocumentContent(Long documentId, byte[] content, boolean lock);
    void deleteDocument(Long documentId);
}
