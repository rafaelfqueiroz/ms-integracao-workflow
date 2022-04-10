package com.github.rafaelfqueiroz.msintegracaoworkflow.repository.documents;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Document("workflows")
public class WorkflowDocument {

    @Id
    @Field(targetType = FieldType.STRING)
    private final UUID id;
    private final String descricao;
    private final String chave;
    private final String idExterno;
    private final List<String> campos;
    private final String url;

}
