package com.pablodev.documentworkspace.services.document;

import com.pablodev.documentworkspace.dto.DocumentInfo;
import com.pablodev.documentworkspace.dto.DocumentRequest;
import com.pablodev.documentworkspace.dto.DocumentContent;

import java.util.List;

public interface DocumentService {
    Long saveDocument(DocumentRequest documentRequest);
    DocumentContent findDocumentContent(Long id);
    DocumentInfo findDocumentInfo(Long id);
    List<DocumentInfo> findAllDocumentInfo();
    void deleteDocument(Long id);
}
