package com.github.rafaelfqueiroz.msintegracaoworkflow.kafka.consumer;

import com.github.rafaelfqueiroz.msintegracaoworkflow.kafka.events.ComandoEvent;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.ComandoService;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.model.Comando;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.model.CriaComando;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComandoEventListener {

    private final ComandoService comandoService;

    @KafkaListener(
            id = "comandoEventListener",
            groupId = "ms-integracao-workflow",
            topics = "${kafka.consumer.comando.topic}",
            containerFactory = "consumerListenerContainerFactory"
    )
    public void onMessage(ConsumerRecord<String, ComandoEvent> consumerRecord) {
        final var comandoEvent = consumerRecord.value();

        final var comando = CriaComando.builder()
                .chaveWorkflow(comandoEvent.getChaveWorkflow())
                .valores(comandoEvent.getValores())
                .build();

        comandoService.handleComando(comando);
    }
}
