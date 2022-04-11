package com.github.rafaelfqueiroz.msintegracaoworkflow.repository;

import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.documents.ComandoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ComandoRepository extends MongoRepository<ComandoDocument, UUID> {

    @Query(value = "{ 'situacao': 'PENDING' }", sort = "{ 'dataCriacao': 1 }")
    List<ComandoDocument> findFirstPendingOrderByDataCriacao();
}
