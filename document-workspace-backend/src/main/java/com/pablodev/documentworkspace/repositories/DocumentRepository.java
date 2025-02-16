package com.pablodev.documentworkspace.repositories;

import com.pablodev.documentworkspace.model.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {
}
