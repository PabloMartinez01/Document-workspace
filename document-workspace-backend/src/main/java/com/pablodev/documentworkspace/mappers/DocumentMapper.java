package com.pablodev.documentworkspace.mappers;

import com.pablodev.documentworkspace.dto.DocumentRequest;
import com.pablodev.documentworkspace.dto.DocumentResponse;
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

    public DocumentResponse toDocumentResponse(Document document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .filename(document.getFilename())
                .extension(document.getExtension())
                .length(document.getLength())
                .content(document.getContent())
                .build();
    }


}
