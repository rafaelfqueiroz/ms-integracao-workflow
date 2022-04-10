package com.github.rafaelfqueiroz.msintegracaoworkflow.consumer;

import com.github.rafaelfqueiroz.msintegracaoworkflow.fixture.ComandoEventFixture;
import com.github.rafaelfqueiroz.msintegracaoworkflow.kafka.consumer.EventConsumerConfig;
import com.github.rafaelfqueiroz.msintegracaoworkflow.kafka.events.ComandoEvent;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.ComandoService;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.model.Comando;
import com.github.rafaelfqueiroz.msintegracaoworkflow.service.model.CriaComando;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.kafka.test.utils.KafkaTestUtils.producerProps;
import static org.testcontainers.utility.DockerImageName.parse;

@EnableKafka
@Testcontainers
@SpringJUnitConfig(
        classes = { ComandoEventListenerITest.class, EventConsumerConfig.class },
        initializers = { ConfigDataApplicationContextInitializer.class }
)
class ComandoEventListenerITest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(parse("confluentinc/cp-kafka").withTag("latest"));

    @Value("${kafka.consumer.comando.topic}")
    private String topic;

    @MockBean
    private ComandoService comandoService;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    private KafkaTemplate<String, ComandoEvent> kafkaTemplate;

    @DynamicPropertySource
    static void setKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("kafka.bootstrap.servers", KAFKA_CONTAINER::getBootstrapServers);
    }

    @BeforeEach
    void setUp() {
        this.kafkaTemplate = new KafkaTemplate<>(
                new DefaultKafkaProducerFactory<>(
                        producerProps(KAFKA_CONTAINER.getBootstrapServers()),
                        new StringSerializer(),
                        new JsonSerializer<>()
                )
        );
        ContainerTestUtils.waitForAssignment(kafkaListenerEndpointRegistry.getListenerContainer("mercadoriaEventListener"), 1);
    }

    @AfterEach
    void cleanUp() {
        this.kafkaTemplate.destroy();
    }

    @Test
    void test_consumesMercadoriaEvent() {
        final var event = ComandoEventFixture.create();
        kafkaTemplate.send(topic, event);

        final var captor = ArgumentCaptor.forClass(CriaComando.class);

        verify(comandoService, times(1)).handleComando(captor.capture());

        final var mercadoria = captor.getValue();
    }
}
