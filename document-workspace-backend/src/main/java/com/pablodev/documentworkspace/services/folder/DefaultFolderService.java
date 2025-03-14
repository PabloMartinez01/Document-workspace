package com.pablodev.documentworkspace.services.folder;

import com.pablodev.documentworkspace.dto.folder.FolderInfo;
import com.pablodev.documentworkspace.dto.folder.FolderRequest;
import com.pablodev.documentworkspace.dto.folder.FolderResponse;
import com.pablodev.documentworkspace.mappers.FolderMapper;
import com.pablodev.documentworkspace.model.Folder;
import com.pablodev.documentworkspace.repositories.FolderRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultFolderService implements FolderService {

    private final FolderRepository folderRepository;
    private final FolderMapper folderMapper;

    @PostConstruct
    public void postConstruct() {
        if (folderRepository.findById(1L).isEmpty()) {
            folderRepository.save(Folder.builder()
                    .name("home")
                    .documents(Collections.emptyList())
                    .subFolders(Collections.emptyList())
                    .parentFolder(null)
                    .build()
            );
        }
    }

    @Override
    public FolderInfo saveFolder(FolderRequest folderRequest) {
        Folder folder = folderMapper.toFolderEntity(folderRequest);
        Folder parentFolder = folderRepository.findById(folderRequest.getParentFolderId())
                .orElseThrow(() -> new EntityNotFoundException("Parent folder not found"));
        folder.setParentFolder(parentFolder);
        Folder savedFolder = folderRepository.save(folder);
        return folderMapper.toFolderInfo(savedFolder);
    }

    @Override
    @Transactional(readOnly = true)
    public FolderResponse findFolderById(Long id) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Folder not found"));
        return folderMapper.toFolderResponse(folder);
    }



}
