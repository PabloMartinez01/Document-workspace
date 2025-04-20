package com.pablodev.documentworkspace.repositories;

import com.pablodev.documentworkspace.model.Document;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {
    @Modifying
    @Query("update Document document set document.locked = :locked where document.id = :id")
    void updateDocumentLock(boolean locked, Long id);


    @Query("SELECT d FROM Document d WHERE " +
            "d.folder.id = :folderId AND " +
            "d.filename LIKE %:filename% AND " +
            "d.extension.type.name IN :types")
    List<Document> findDocumentsByFilter(@Param("folderId") Long folderId,
                                   @Param("filename") String filename,
                                   @Param("types") List<String> types);
}
