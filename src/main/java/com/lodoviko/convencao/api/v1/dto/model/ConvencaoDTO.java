package com.lodoviko.convencao.api.v1.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class ConvencaoDTO extends RepresentationModel<ConvencaoDTO> {
        private Long sqConvencao;
        private String dsReduzido;
        private String dsConvencao;
        private String imLogo;
        private String dsEndereco;
        private String dsBairro;
        private String dsCidade;
        private String dsPais;
        private String dsCep;
        private EstadoDTO estado;
        private String dsCnpj;
        private String dsEmail;
        private String dsTelefones;
        private String dsWatsapp;
}
