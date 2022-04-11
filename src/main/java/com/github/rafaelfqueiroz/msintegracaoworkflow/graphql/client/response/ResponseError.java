package com.github.rafaelfqueiroz.msintegracaoworkflow.graphql.client.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ResponseError {

    private String message;
    private List<Object> locations;
    private List<Object> path;
    private Object extensions;

}
