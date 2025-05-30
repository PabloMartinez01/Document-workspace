package com.pablodev.documentworkspace.repositories;

import com.pablodev.documentworkspace.model.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends CrudRepository<Type, Integer> {
    Optional<Type> findByName(String name);

    @Query("SELECT e.type FROM Extension e WHERE e.name = :extension")
    Optional<Type> findByExtension(@Param("extension") String extension);

    @Query("SELECT t FROM Type t WHERE t.name = 'other'")
    Type findDefault();

}
