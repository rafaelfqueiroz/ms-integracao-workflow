package com.github.rafaelfqueiroz.msintegracaoworkflow.fixture;

import com.github.rafaelfqueiroz.msintegracaoworkflow.kafka.events.ComandoEvent;

public class ComandoEventFixture {

    public static ComandoEvent create() {
        return ComandoEvent.builder().build();
    }
}
