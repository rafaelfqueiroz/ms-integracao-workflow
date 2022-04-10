package com.github.rafaelfqueiroz.msintegracaoworkflow.repository.documents;

import com.github.rafaelfqueiroz.msintegracaoworkflow.service.model.Execucao;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.model.SituacaoComando;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@Document("comandos")
public class ComandoDocument {

    @Id
    @Field(targetType = FieldType.STRING)
    private final UUID id;
    private final Map<String, Object> valores;
    private final String chaveWorkflow;
    private final SituacaoComando situacao;
    private final Instant dataCriacao;
    @Builder.Default
    private final List<Execucao> execucoes = new ArrayList<>();

}
