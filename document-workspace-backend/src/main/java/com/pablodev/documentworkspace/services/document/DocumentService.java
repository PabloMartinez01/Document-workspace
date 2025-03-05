package com.pablodev.documentworkspace.services.document;

import com.pablodev.documentworkspace.dto.document.DocumentContent;
import com.pablodev.documentworkspace.dto.document.DocumentInfo;
import com.pablodev.documentworkspace.dto.document.DocumentRequest;

import java.util.List;

public interface DocumentService {
    DocumentInfo saveDocument(DocumentRequest documentRequest);
    DocumentContent findDocumentContent(Long id);
    DocumentInfo findDocumentInfo(Long id);
    List<DocumentInfo> findAllDocumentInfo();
    void updateDocumentLock(Long id, boolean locked);
    void updateDocumentContent(Long id, byte[] content, boolean lock);
    void deleteDocument(Long id);
}
