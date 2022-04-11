package com.github.rafaelfqueiroz.msintegracaoworkflow.service;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class PipefyClientIntegration implements WorkflowIntegration {

    private final WebClient webClient;
    private final String query;
    private final String authorizationToken;
    private final long pipeId;
    private final long phaseId;

    public PipefyClientIntegration(@Qualifier("createCardMutation") String query,
                                   @Value("${workflow.service.pipefy.token}") String token,
                                   @Value("${workflow.service.pipefy.pipeId}") long pipeId,
                                   @Value("${workflow.service.pipefy.phaseId}") long phaseId,
                                   WebClient webClient) {
        this.query = query;
        this.webClient = webClient;
        this.authorizationToken = token;
        this.pipeId = pipeId;
        this.phaseId =  phaseId;
    }

    public void startWorkflow(Map<String, Object> providedValues) {

        final var requestBody = new CreateCardRequestBody(query, pipeId, phaseId, providedValues);

        Optional<CreateCardResponse.CardData.Card> card = webClient.post()
                .uri("/graphql")
                .header("Authorization", "Bearer " + authorizationToken)
                .bodyValue(requestBody)
                .exchangeToMono((clientResponse -> clientResponse.toEntity(CreateCardResponse.class)))
                .flatMap(this::mapper)
                .map(CreateCardResponse::getData)
                .map(CreateCardResponse.CardData::getCard)
                .doOnError((exception) -> log.error("Erro na resposta da integração.", exception))
                .blockOptional();

        card.isPresent();
    }

    private Mono<CreateCardResponse> mapper(ResponseEntity<CreateCardResponse> response) {
        final var responseBody = response.getBody();
        return Mono.empty();
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateCardRequestBody {
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

    @Getter
    @RequiredArgsConstructor
    public static class CreateCardVariables {

        private final CreateCardInput createCardData;

    }

    @Getter
    @RequiredArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateCardInput {
        private final long pipeId;
        private final long phaseId;
        private final List<CreateCardField> fieldsAttributes;
    }

    @Getter
    @RequiredArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CreateCardField {
        private final String fieldId;
        private final Object fieldValue;
    }

    @Getter
    @ToString
    public static class CreateCardResponse {
        private CardData data;
        private List<ResponseError> errors;

        @Getter
        @ToString
        public static class CardData {
            private Card card;

            @Getter
            @Builder(toBuilder = true)
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Card {
                private String id;
                private String title;
            }
        }

        @Getter
        @ToString
        public static class ResponseError {
            private String message;
            private List<Object> locations;
            private List<Object> path;
            private Object extensions;
        }
    }

}
