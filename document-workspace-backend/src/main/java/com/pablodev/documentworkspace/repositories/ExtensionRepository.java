package com.pablodev.documentworkspace.repositories;

import com.pablodev.documentworkspace.model.Extension;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtensionRepository extends CrudRepository<Extension, Integer> {
    Optional<Extension> findByName(String name);
}
