package com.pablodev.documentworkspace.repositories;

import com.pablodev.documentworkspace.model.Folder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FolderRepository extends CrudRepository<Folder, Long> {

    @Query("SELECT s FROM Folder s WHERE s.parentFolder.id = :folderId AND s.name LIKE %:name%")
    List<Folder> findFilteredSubFoldersByFolderId(@Param("folderId") Long folderId, @Param("name") String name);
}
