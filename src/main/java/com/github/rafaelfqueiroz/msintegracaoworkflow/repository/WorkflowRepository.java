package com.github.rafaelfqueiroz.msintegracaoworkflow.repository;

import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.documents.WorkflowDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkflowRepository extends MongoRepository<WorkflowDocument, UUID> {

    @Query("{'chave': :#{#chave} }")
    Optional<WorkflowDocument> findByChave(@Param("chave") String chave);
}
