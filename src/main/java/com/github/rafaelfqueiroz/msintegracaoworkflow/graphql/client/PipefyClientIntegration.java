package com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client;

import com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client.request.CreateCardRequestBody;
import com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client.response.Card;
import com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client.response.CreateCardResponse;
import com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client.response.CreatedCardData;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.WorkflowIntegration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

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

        Optional<Card> card = webClient.post()
                .uri("/graphql")
                .header("Authorization", "Bearer " + authorizationToken)
                .bodyValue(requestBody)
                .exchangeToMono((clientResponse -> clientResponse.toEntity(CreateCardResponse.class)))
                .flatMap(this::mapper)
                .map(CreateCardResponse::getData)
                .map(CreatedCardData::getCreatedCard)
                .doOnError((exception) -> log.error("Erro na resposta da integração.", exception))
                .blockOptional();

        card.isPresent();
    }

    private Mono<CreateCardResponse> mapper(ResponseEntity<CreateCardResponse> response) {
        final var responseBody = response.getBody();
        return Mono.empty();
    }

}
