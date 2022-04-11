package com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    private CardDetails card;

}
