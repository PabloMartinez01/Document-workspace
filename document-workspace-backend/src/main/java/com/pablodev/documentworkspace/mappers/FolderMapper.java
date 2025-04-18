package com.pablodev.documentworkspace.mappers;

import com.pablodev.documentworkspace.dto.folder.FolderInfoResponse;
import com.pablodev.documentworkspace.dto.folder.FolderItemsResponse;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;
import com.pablodev.documentworkspace.model.Document;
import com.pablodev.documentworkspace.model.Folder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class FolderMapper {

    private final DocumentMapper documentMapper;

    public FolderMapper(@Lazy DocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    public Folder toFolderEntity(FolderRequest folderRequest) {
        return Folder.builder()
                .name(folderRequest.getName())
                .documents(Collections.emptyList())
                .subFolders(Collections.emptyList())
                .build();
    }

    public FolderInfoResponse toFolderInfo(Folder folder) {
        return Optional.ofNullable(folder)
                .map(f -> FolderInfoResponse.builder()
                        .id(f.getId())
                        .name(f.getName())
                        .createdDate(f.getCreatedDate())
                        .lastModifiedDate(f.getLastModifiedDate())
                        .build())
                .orElse(null);
    }

    public FolderResponse toFolderResponse(Folder folder) {
        return FolderResponse.builder()
                .id(folder.getId())
                .name(folder.getName())
                .parentFolder(toFolderInfo(folder.getParentFolder()))
                .folders(folder
                        .getSubFolders()
                        .stream()
                        .map(this::toFolderInfo)
                        .toList()
                )
                .documents(folder
                        .getDocuments()
                        .stream()
                        .map(documentMapper::toDocumentInfo)
                        .toList()
                )
                .createdDate(folder.getCreatedDate())
                .lastModifiedDate(folder.getLastModifiedDate())
                .build();
    }

    public FolderItemsResponse toFolderItemsResponse(
            List<Folder> folders,
            List<Document> documents
    ) {
        return FolderItemsResponse.builder()
                .folders(folders.stream()
                        .map(this::toFolderInfo)
                        .toList()
                )
                .documents(documents.stream()
                        .map(documentMapper::toDocumentInfo)
                        .toList()
                )
                .build();
    }

}
