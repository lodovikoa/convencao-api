package com.lodoviko.convencao.domain.exception;

public class RecursoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RecursoNaoEncontradoException(String entidade, Long sq) {
        this(String.format("Não existe cadastro de %s com ID %d", entidade, sq));
    }
}
