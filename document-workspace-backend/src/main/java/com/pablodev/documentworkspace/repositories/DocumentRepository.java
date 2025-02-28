package com.pablodev.documentworkspace.repositories;

import com.pablodev.documentworkspace.model.Document;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {
    @Modifying
    @Query("update Document document set document.open = :open where document.id = :id")
    void updateDocumentOpenStatus(boolean open, Long id);
}
