package com.pablodev.documentworkspace.repositories;

import com.pablodev.documentworkspace.model.Document;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Document, Long> {
}
