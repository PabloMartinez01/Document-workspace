package com.pablodev.documentworkspace.services.folder;

import com.pablodev.documentworkspace.dto.folder.FolderInfo;
import com.pablodev.documentworkspace.dto.folder.FolderItemsResponse;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;

public interface FolderService {
    FolderInfo saveFolder(FolderRequest folderRequest);
    FolderResponse findFolderById(Long folder);
    FolderItemsResponse findFolderItemsByName(Long folderId, String name);
}
