package com.lodoviko.convencao.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_utr_trancode")
public class Trancode {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utr_sq_trancode")
    private Long sqTrancode;

    @Column(name = "utr_ds_trancode", length = 50)
    private String dsTrancode;

    @ManyToOne
    @JoinColumn(name = "ugr_sq_grupo", nullable = true)
    private Grupo grupo;

    @CreationTimestamp
    @Column(name = "auditoria_data")
    private OffsetDateTime auditoriaData;

    @Column(name = "auditoria_usuario", length = 50)
    private String auditoriaUsuario;
}
