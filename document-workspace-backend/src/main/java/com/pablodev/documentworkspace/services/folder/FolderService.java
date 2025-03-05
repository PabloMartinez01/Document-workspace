package com.pablodev.documentworkspace.services.folder;

import com.pablodev.documentworkspace.dto.document.DocumentRequest;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;

public interface FolderService {

    FolderResponse createFolder(FolderRequest folderRequest);
    FolderResponse findFolderById(Long id);
    FolderResponse saveDocumentInFolder(Long id, DocumentRequest documentRequest);

}
