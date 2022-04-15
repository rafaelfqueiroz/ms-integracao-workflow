package com.github.rafaelfqueiroz.msintegracaoworkflow.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class ComandoEvent {

    private String chaveWorkflow;
    private Map<String, Object> valores;

}