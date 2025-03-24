package com.pablodev.documentworkspace.services.folder;

import com.pablodev.documentworkspace.dto.folder.FolderInfoResponse;
import com.pablodev.documentworkspace.dto.folder.FolderItemsResponse;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;

public interface FolderService {
    FolderInfoResponse saveFolder(FolderRequest folderRequest);
    FolderResponse findFolderById(Long folder);
    FolderItemsResponse findFolderItemsByName(Long folderId, String name);
}
