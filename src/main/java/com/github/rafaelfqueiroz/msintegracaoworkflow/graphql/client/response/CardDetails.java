package com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CardDetails {

    private String id;
    private String title;

}
