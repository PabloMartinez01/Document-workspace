package com.pablodev.documentworkspace.mappers;

import com.pablodev.documentworkspace.dto.document.DocumentContent;
import com.pablodev.documentworkspace.dto.document.DocumentInfo;
import com.pablodev.documentworkspace.model.Document;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class DocumentMapper {

    private FolderMapper folderMapper;

    public DocumentMapper(@Lazy FolderMapper folderMapper) {
        this.folderMapper = folderMapper;
    }

    public Document toDocumentEntity(MultipartFile multipartFile) throws IOException {
        return Document.builder()
                .filename(multipartFile.getOriginalFilename())
                .extension(multipartFile.getOriginalFilename()
                        .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1))
                .length(multipartFile.getSize())
                .version(0L)
                .locked(false)
                .content(multipartFile.getBytes())
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
                .folder(folderMapper.toFolderInfo(document.getFolder()))
                .build();
    }


}
