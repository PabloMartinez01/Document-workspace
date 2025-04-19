package com.pablodev.documentworkspace.repositories;

import com.pablodev.documentworkspace.model.Folder;
import org.springframework.data.repository.CrudRepository;

public interface FolderRepository extends CrudRepository<Folder, Long> {
    boolean existsByParentFolderAndName(Folder parentFolder, String name);
    boolean existsById(Long folderId);

}
