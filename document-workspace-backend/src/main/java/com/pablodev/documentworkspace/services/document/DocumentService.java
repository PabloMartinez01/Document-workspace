package com.pablodev.documentworkspace.services.document;

import com.pablodev.documentworkspace.dto.DocumentContent;
import com.pablodev.documentworkspace.dto.DocumentInfo;
import com.pablodev.documentworkspace.dto.DocumentRequest;

import java.util.List;

public interface DocumentService {
    DocumentInfo saveDocument(DocumentRequest documentRequest);
    DocumentContent findDocumentContent(Long id);
    DocumentInfo findDocumentInfo(Long id);
    List<DocumentInfo> findAllDocumentInfo();
    void updateDocumentOpenStatus(Long id, boolean open);
    void deleteDocument(Long id);
}
