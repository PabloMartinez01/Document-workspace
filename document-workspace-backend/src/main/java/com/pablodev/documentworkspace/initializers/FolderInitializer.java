package com.pablodev.documentworkspace.initializers;

import com.pablodev.documentworkspace.model.Folder;
import com.pablodev.documentworkspace.repositories.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class FolderInitializer implements CommandLineRunner {

    private final FolderRepository folderRepository;

    @Override
    public void run(String... args) throws Exception {
        if (folderRepository.findById(1L).isEmpty()) {
            folderRepository.save(Folder.builder()
                    .name("home")
                    .documents(Collections.emptyList())
                    .subFolders(Collections.emptyList())
                    .parentFolder(null)
                    .createdDate(LocalDateTime.now())
                    .createdBy("system")
                    .build()
            );
        }
    }
}
