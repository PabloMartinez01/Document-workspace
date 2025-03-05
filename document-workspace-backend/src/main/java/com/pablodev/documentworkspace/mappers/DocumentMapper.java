package com.pablodev.documentworkspace.mappers;

import com.pablodev.documentworkspace.dto.document.DocumentContent;
import com.pablodev.documentworkspace.dto.document.DocumentInfo;
import com.pablodev.documentworkspace.dto.document.DocumentRequest;
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
                .version(0L)
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
                .locked(document.isLocked())
                .version(document.getVersion())
                .build();
    }


}
