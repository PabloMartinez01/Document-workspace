package com.pablodev.documentworkspace.mappers;

import com.pablodev.documentworkspace.dto.folder.FolderInfo;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;
import com.pablodev.documentworkspace.model.Folder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class FolderMapper {

    private DocumentMapper documentMapper;

    public FolderMapper(@Lazy DocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    public Folder toFolderEntity(FolderRequest folderRequest) {
        return Folder.builder()
                .name(folderRequest.getName())
                .documents(new ArrayList<>())
                .subFolders(new ArrayList<>())
                .build();
    }

    public FolderInfo toFolderInfo(Folder folder) {

        if (folder == null) return null;

        return FolderInfo.builder()
                .id(folder.getId())
                .name(folder.getName())
                .build();
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
