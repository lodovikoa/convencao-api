package com.lodoviko.convencao.domain.exception;

public class EmailException extends NegocioException{

    public EmailException(String mensagem) {
        super(mensagem);
    }

    public EmailException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
