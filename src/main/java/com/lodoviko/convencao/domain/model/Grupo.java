package com.lodoviko.convencao.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_ugr_grupo")
public class Grupo {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ugr_sq_grupo")
    private Long sqGrupo;

    @Column(name = "ugr_is_situacao")
    private boolean isSituacao;

    @Column(name = "ugr_ds_nome", length = 30)
    private String dsNome;

}
