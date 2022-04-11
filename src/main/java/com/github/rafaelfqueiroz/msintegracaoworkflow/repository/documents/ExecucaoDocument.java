package com.github.rafaelfqueiroz.msintegracaoworkflow.repository.documents;

import com.github.rafaelfqueiroz.msintegracaoworkflow.service.model.SituacaoComando;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
public class ExecucaoDocument {

    @Field(targetType = FieldType.STRING)
    private final UUID id;
    private final SituacaoComando situacao;
    private final Instant dataCriacao;

}
