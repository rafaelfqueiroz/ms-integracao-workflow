package com.github.rafaelfqueiroz.msintegracaoworkflow.kafka.events;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@RequiredArgsConstructor
public final class ComandoEvent {

    private String chaveWorkflow;
    private Map<String, Object> valores;

}