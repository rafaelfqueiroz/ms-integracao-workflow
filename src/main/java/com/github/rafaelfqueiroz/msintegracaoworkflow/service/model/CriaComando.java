package com.github.rafaelfqueiroz.msintegracaoworkflow.service.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class CriaComando {

    private String chaveWorkflow;
    private Map<String, Object> valores;

}
