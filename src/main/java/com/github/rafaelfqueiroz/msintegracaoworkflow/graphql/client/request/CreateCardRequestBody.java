package com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateCardRequestBody {
    private final String query;
    private final CreateCardVariables variables;

    public CreateCardRequestBody(String query,
                                 long pipeId,
                                 long phaseId,
                                 Map<String, Object> values) {
        this.query = query;
        final var createCardInput = new CreateCardInput(
                pipeId,
                phaseId,
                List.of(
                        new CreateCardField("nome_cliente", values.get("nomeCliente")),
                        new CreateCardField("email_cliente", values.get("emailCliente")),
                        new CreateCardField("c_digo_cliente", values.get("codigoMercadoria"))
                )
        );
        this.variables = new CreateCardVariables(createCardInput);
    }
}
