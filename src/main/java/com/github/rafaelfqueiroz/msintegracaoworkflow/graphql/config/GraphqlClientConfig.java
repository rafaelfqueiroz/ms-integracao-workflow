package com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.config;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class GraphqlClientConfig {

    @Value("${workflow.service.baseUrl}")
    private String baseUrl;
    @Value("${workflow.service.connectTimeoutMs}")
    private int connectTimeoutMs;
    @Value("${workflow.service.responseTimeoutMs}")
    private int responseTimeoutMs;

    @Bean("createCardMutation")
    public String createCardMutation(
            @Value("classpath:graphql/create_card_mutation.graphql") Resource mutationFile) throws IOException {
        return new String(Files.readAllBytes(mutationFile.getFile().toPath()));
    }

    @Bean
    public WebClient graphqlWebClient() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMillis(responseTimeoutMs))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMs);

        final var webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return webClient;
    }
}
