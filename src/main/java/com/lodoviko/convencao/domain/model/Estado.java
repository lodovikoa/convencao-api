package com.lodoviko.convencao.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_est_estado")
public class Estado {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "est_sq_estado")
    private Long sqEstado;

    @Column(name = "est_ds_uf", length = 2)
    private String dsUf;

    @Column(name = "est_ds_nome", length = 50)
    private String dsNome;

    @CreationTimestamp
    @Column(name = "auditoria_data")
    private OffsetDateTime auditoriaData;

    @Column(name = "auditoria_usuario", length = 50)
    private String auditoriaUsuario;
}
