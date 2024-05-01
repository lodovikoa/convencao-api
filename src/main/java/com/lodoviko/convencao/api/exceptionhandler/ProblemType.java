package com.lodoviko.convencao.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro Inválido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    RECURSO_JA_CADASTRADO("/recurso-já-cadastrado", "Recurso já cadastrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de Sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://convencao.com.br" + path;
        this.title = title;
    }
}
