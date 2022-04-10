package com.github.rafaelfqueiroz.msintegracaoworkflow.service.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
public final class Execucao {
    private final UUID id;
    private final SituacaoExececucao situacao;
    private final Instant dataCriacao;
}
