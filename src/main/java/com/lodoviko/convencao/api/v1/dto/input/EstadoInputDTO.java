package com.lodoviko.convencao.api.v1.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInputDTO {

    @NotBlank
    private String dsUf;

    @NotBlank
    private String dsNome;
}
