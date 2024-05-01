package com.lodoviko.convencao.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private Integer cdStatus;
    private OffsetDateTime dtData;
    private String dsTipo;
    private String dsTitulo;
    private String dsDetalhe;
    private String dsMensUsuario;
    private List<Objeto> lsObjetos;

    @Getter
    @Builder
    public static class Objeto {
        private String dsNome;
        private String dsMensUsuario;
    }
}
