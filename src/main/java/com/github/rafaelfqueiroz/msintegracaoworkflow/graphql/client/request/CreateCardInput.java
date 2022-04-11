package com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateCardInput {

    private final long pipeId;
    private final long phaseId;
    private final List<CreateCardField> fieldsAttributes;

}
