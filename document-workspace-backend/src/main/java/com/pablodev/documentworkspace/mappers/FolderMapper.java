package com.pablodev.documentworkspace.mappers;

import com.pablodev.documentworkspace.dto.folder.FolderInfo;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;
import com.pablodev.documentworkspace.model.Folder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class FolderMapper {

    private DocumentMapper documentMapper;

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

    public FolderInfo toFolderInfo(Folder folder) {
        return Optional.ofNullable(folder)
                .map(f -> FolderInfo.builder()
                        .id(f.getId())
                        .name(f.getName())
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
                .build();
    }

}
