package com.pablodev.documentworkspace.services.folder;

import com.pablodev.documentworkspace.model.Folder;
import com.pablodev.documentworkspace.repositories.FolderRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultFolderService {

    private final FolderRepository folderRepository;

    @PostConstruct
    public void postConstruct() {
        folderRepository.findById(1L).ifPresentOrElse(folder -> {}, () -> {
            folderRepository.save(
                Folder.builder()
                        .filename("home")
                        .elements(new ArrayList<>())
                        .build()
            );
        });
    }

    public void createFolder(String filename) {
        folderRepository.save(
                Folder.builder()
                        .filename(filename)
                        .elements(new ArrayList<>())
                        .build()
        );
    }











}
