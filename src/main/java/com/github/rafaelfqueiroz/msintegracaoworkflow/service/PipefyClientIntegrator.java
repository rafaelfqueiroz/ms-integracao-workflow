package com.github.rafaelfqueiroz.msintegracaoworkflow.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class PipefyClientIntegrator {

    private final String query;
    private final String authorizationToken;
    private final WebClient webClient;

    public PipefyClientIntegrator(@Qualifier("createCardMutation") String query,
                                  @Value("${workflow.service.token}") String token,
                                  WebClient webClient) {
        this.query = query;
        this.webClient = webClient;
        this.authorizationToken = token;
    }

    public void startWorkflow(List<String> requiredValues,
                              Map<String, Object> providedValues) {

        Optional<ResponseEntity<Void>> response = webClient.post()
                .uri("/graphql")
                .header("Authorization", "Bearer " + authorizationToken)
                .bodyValue(new CreateCardRequest(query, providedValues))
                .retrieve()
                .toBodilessEntity()
                .blockOptional();
//                .exchangeToMono(response -> response.toEntity(CreateCardResponse.class))

    }

    @Getter
    public static class CreateCardRequest {
        private final String query;
        private final CreateCardVariables variables;

        public CreateCardRequest(String query,
                                 Map<String, Object> values) {
            this.query = query;
            Object pipeId = values.get("pipeId");
            Object phaseId = values.get("phaseId");
            Object nomeCliente = values.get("nomeCliente");
            Object emailCliente = values.get("emailCliente");
            Object codigoMercadoria = values.get("codigoMercadoria");
            this.variables = new CreateCardVariables(pipeId, phaseId, nomeCliente, emailCliente, codigoMercadoria);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class CreateCardVariables {
        private final Object pipeId;
        private final Object phaseId;
        private final Object nomeCliente;
        private final Object emailCliente;
        private final Object codigoMercadoria;
    }

    /*@Getter
    public static class CreateCardResponse {
        private f
    }*/

}
