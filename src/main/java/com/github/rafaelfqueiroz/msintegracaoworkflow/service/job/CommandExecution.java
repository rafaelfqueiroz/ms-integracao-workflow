package com.github.rafaelfqueiroz.msintegracaoworkflow.service.job;

import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.ComandoRepository;
import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.WorkflowRepository;
import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.documents.ComandoDocument;
import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.documents.WorkflowDocument;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.WorkflowIntegration;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.model.Execucao;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.model.SituacaoComando;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public final class CommandExecution {

    private final ComandoRepository comandoRepository;
    private final WorkflowRepository workflowRepository;
    private final WorkflowIntegration workflowIntegration;

    /**
     * Executa a cada 2 minutos, com delay inicial de 1 segundo.
     */
    @Scheduled(initialDelay = 1L, fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void executaComandoAsync() {
        List<ComandoDocument> comandosPendentes = comandoRepository.findFirstPendingOrderByDataCriacao();

        if (comandosPendentes.isEmpty()) {
            log.info("Nenhum comando pendente de execução.");
            return;
        }
        final var comandoDocument = comandosPendentes.get(0);
        Optional<WorkflowDocument> workflowOptional = workflowRepository.findByChave(comandoDocument.getChaveWorkflow());

        if (!workflowOptional.isPresent()) {
            log.warn("Workflow não encontrado: {}", comandoDocument.getChaveWorkflow());
            comandoDocument.setSituacao(SituacaoComando.FAILED);
            comandoRepository.save(comandoDocument);
            return;
        }
        WorkflowDocument workflowDocument = workflowOptional.get();

        boolean containsRequiredValues = workflowDocument.getCampos().stream().allMatch((campo) -> comandoDocument.getValores().get(campo) != null);

        SituacaoComando situacaoComando;
        if (containsRequiredValues) {
            try {
                workflowIntegration.startWorkflow(comandoDocument.getValores());
                situacaoComando = SituacaoComando.EXECUTED;
            } catch (Exception e) {
                log.error("Falha na integração.", e);
                situacaoComando = SituacaoComando.FAILED;
            }
        } else {
            situacaoComando = SituacaoComando.FAILED;
        }
        final var execucaoAtual = Execucao.builder()
                .id(UUID.randomUUID())
                .situacao(situacaoComando)
                .dataCriacao(Instant.now())
                .build();

        comandoDocument.getExecucoes().add(execucaoAtual);
        comandoDocument.setSituacao(situacaoComando);
        comandoRepository.save(comandoDocument);
    }
}
