package com.lodoviko.convencao.domain.exception;

public class RecursoBloqueadoException extends NegocioException{

    public RecursoBloqueadoException(String mensagem) {
        super(mensagem);
    }

    public RecursoBloqueadoException(String mensagem, Throwable causa) {
        this(String.format("%s", mensagem));
    }
}
