package com.lodoviko.convencao.api.v1.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {

    private Long sqEstado;

    private String dsUf;

    private String dsNome;

}
