package com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateCardVariables {

    private final CreateCardInput createCardData;

}
