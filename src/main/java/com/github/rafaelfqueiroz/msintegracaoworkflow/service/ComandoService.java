package com.github.rafaelfqueiroz.msintegracaoworkflow.service;

import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.ComandoRepository;
import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.documents.ComandoDocument;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.model.CriaComando;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.model.SituacaoComando;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public final class ComandoService {

    private final ComandoRepository comandoRepository;

    public void handleComando(CriaComando criaComando) {

        final var comandoDocument = ComandoDocument.builder()
                .id(UUID.randomUUID())
                .valores(criaComando.getValores())
                .chaveWorkflow(criaComando.getChaveWorkflow())
                .dataCriacao(Instant.now())
                .situacao(SituacaoComando.PENDING)
                .build();

        comandoRepository.save(comandoDocument);
    }

}
