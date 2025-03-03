package com.pablodev.documentworkspace.mappers;

import com.pablodev.documentworkspace.dto.DocumentContent;
import com.pablodev.documentworkspace.dto.DocumentInfo;
import com.pablodev.documentworkspace.dto.DocumentRequest;
import com.pablodev.documentworkspace.model.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public Document toDocumentEntity(DocumentRequest documentRequest) {
        return Document.builder()
                .filename(documentRequest.getFilename())
                .extension(documentRequest.getExtension())
                .content(documentRequest.getContent())
                .length(documentRequest.getLength())
                .build();
    }

    public DocumentContent toDocumentContent(Document document) {
        return DocumentContent.builder()
                .filename(document.getFilename())
                .content(document.getContent())
                .build();
    }

    public DocumentInfo toDocumentInfo(Document document) {
        return DocumentInfo.builder()
                .id(document.getId())
                .extension(document.getExtension())
                .filename(document.getFilename())
                .length(document.getLength())
                .open(document.isLocked())
                .build();
    }


}
