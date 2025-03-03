package com.pablodev.documentworkspace.repositories;

import com.pablodev.documentworkspace.model.Document;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {
    @Modifying
    @Query("update Document document set document.locked = :locked where document.id = :id")
    void updateDocumentLock(boolean locked, Long id);
}
