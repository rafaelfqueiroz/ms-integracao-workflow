package com.github.rafaelfqueiroz.msintegracaoworkflow.service;

import com.github.rafaelfqueiroz.msintegracaoworkflow.repository.WorkflowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkflowService {

    private final WorkflowRepository workflowRepository;



}
