package com.pablodev.documentworkspace.mappers;

import com.pablodev.documentworkspace.dto.folder.FolderInfo;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;
import com.pablodev.documentworkspace.model.Folder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class FolderMapper {

    private final DocumentMapper documentMapper;

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
