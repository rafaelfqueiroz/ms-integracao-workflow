package com.github.rafaelfqueiroz.msintegracaoworkflow.service;

import java.util.Map;

public interface WorkflowIntegration {

    void startWorkflow(Map<String, Object> providedValues);
}
