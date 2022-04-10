package com.github.rafaelfqueiroz.msintegracaoworkflow.service.job;

import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.ComandoRepository;
import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.WorkflowRepository;
import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.documents.ComandoDocument;
import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.documents.WorkflowDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public final class CommandExecution {

    private final ComandoRepository comandoRepository;
    private final WorkflowRepository workflowRepository;

    /**
     * Executa a cada 2 minutos, com delay inicial de 1 segundo.
     */
    @Scheduled(initialDelay = 1L, fixedRate = 120L, timeUnit = TimeUnit.SECONDS)
    public void executaComandoAsync() {
        Optional<ComandoDocument> comandoDocumentOptional = comandoRepository.findFirstPendingOrderByDataCriacao();
        if (!comandoDocumentOptional.isPresent()) {
            log.info("Nenhum comando pendente de execução.");
            return;
        }
        //TODO fazer o parse dos comandos para dentro do
        final var comandoDocument = comandoDocumentOptional.get();
        Optional<WorkflowDocument> workflowOptional = workflowRepository.findByChave(comandoDocument.getChaveWorkflow());

        if (!workflowOptional.isPresent()) {
            log.warn("Workflow não encontrado: {}", comandoDocument.getChaveWorkflow());
            return;
        }



    }
}
