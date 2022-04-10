package com.github.rafaelfqueiroz.msintegracaoworkflow.service.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
public final class Comando {

    private final UUID id;
    private final Map<String, Object> valores;
    private final String codigoWorkflow;
    private final SituacaoComando situacao;
    private final Instant dataCriacao;
    @Builder.Default
    private final List<Execucao> execucoes = new ArrayList<>();

}
