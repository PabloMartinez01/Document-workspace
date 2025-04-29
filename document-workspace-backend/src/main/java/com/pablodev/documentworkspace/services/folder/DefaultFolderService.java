package com.pablodev.documentworkspace.services.folder;

import com.pablodev.documentworkspace.dto.folder.FolderInfoResponse;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;
import com.pablodev.documentworkspace.mappers.FolderMapper;
import com.pablodev.documentworkspace.model.Folder;
import com.pablodev.documentworkspace.repositories.FolderRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultFolderService implements FolderService {

    private final FolderRepository folderRepository;
    private final FolderMapper folderMapper;

    @Override
    public FolderInfoResponse saveFolder(FolderRequest folderRequest) {
        Folder folder = folderMapper.toFolderEntity(folderRequest);

        Folder parentFolder = folderRepository.findById(folderRequest.getParentFolderId())
                .orElseThrow(() -> new EntityNotFoundException("Parent folder not found"));

        if (folderRepository.existsByParentFolderAndName(parentFolder, folder.getName()))
            throw new EntityExistsException("The folder name already exists");

        folder.setParentFolder(parentFolder);
        Folder savedFolder = folderRepository.save(folder);
        return folderMapper.toFolderInfo(savedFolder);
    }

    @Override
    @Transactional(readOnly = true)
    public FolderResponse findFolderById(Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new EntityNotFoundException("Folder not found"));
        return folderMapper.toFolderResponse(folder);
    }

    @Override
    public void deleteFolderById(Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new EntityNotFoundException("Folder not found"));
        folderRepository.delete(folder);
    }


}
