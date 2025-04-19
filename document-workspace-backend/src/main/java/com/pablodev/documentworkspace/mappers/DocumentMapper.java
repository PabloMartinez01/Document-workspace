package com.pablodev.documentworkspace.mappers;

import com.pablodev.documentworkspace.dto.document.DocumentContentResponse;
import com.pablodev.documentworkspace.dto.document.DocumentResponse;
import com.pablodev.documentworkspace.model.Document;
import com.pablodev.documentworkspace.utils.DocumentUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class DocumentMapper {

    private FolderMapper folderMapper;
    private DocumentUtils documentUtils;

    public DocumentMapper(@Lazy FolderMapper folderMapper, DocumentUtils documentUtils) {
        this.folderMapper = folderMapper;
        this.documentUtils = documentUtils;
    }

    public Document toDocumentEntity(MultipartFile multipartFile) throws IOException {
        return Document.builder()
                .filename(multipartFile.getOriginalFilename())
                .extension(documentUtils.getExtensionFromMultipart(multipartFile))
                .length(multipartFile.getSize())
                .version(0L)
                .locked(false)
                .content(multipartFile.getBytes())
                .build();
    }

    public DocumentContentResponse toDocumentContent(Document document) {
        return DocumentContentResponse.builder()
                .filename(document.getFilename())
                .content(document.getContent())
                .build();
    }

    public DocumentResponse toDocumentResponse(Document document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .extension(document.getExtension())
                .filename(document.getFilename())
                .length(document.getLength())
                .locked(document.isLocked())
                .version(document.getVersion())
                .type(document.getType().getName())
                .createdDate(document.getCreatedDate())
                .lastModifiedDate(document.getLastModifiedDate())
                .folder(folderMapper.toFolderInfo(document.getFolder()))
                .build();
    }


}
