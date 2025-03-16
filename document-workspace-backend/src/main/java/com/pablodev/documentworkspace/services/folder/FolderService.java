package com.pablodev.documentworkspace.services.folder;

import com.pablodev.documentworkspace.dto.folder.FolderInfo;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;

public interface FolderService {
    FolderInfo saveFolder(FolderRequest folderRequest);
    FolderResponse findFolderById(Long folder);
    FolderResponse findFolderWithFilteredItems(Long folderId, String name);
}
