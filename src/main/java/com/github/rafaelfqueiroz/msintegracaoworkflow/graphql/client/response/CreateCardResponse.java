package com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class CreateCardResponse {

    private CreatedCardData data;
    private List<ResponseError> errors;

}
