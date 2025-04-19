package com.pablodev.documentworkspace.mappers;

import com.onlyoffice.model.documenteditor.config.document.DocumentType;
import com.onlyoffice.model.documenteditor.config.document.Type;
import org.springframework.stereotype.Component;

@Component
public class DocumentTypeMapper {

    public DocumentType toDocumentType(Type type) {
        return DocumentType.valueOf(type.name().toUpperCase());
    }

    public DocumentType toDocumentType(String type) {
        return DocumentType.valueOf(type.toUpperCase());
    }

}
