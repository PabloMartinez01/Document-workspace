package com.pablodev.documentworkspace.services.folder;

import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;

public interface FolderService {
    FolderResponse saveFolder(FolderRequest folderRequest);
    FolderResponse findFolderById(Long id);
}
