package com.lodoviko.convencao.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_con_convencao")
public class Convencao {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "con_sq_convencao")
    private Long sqConvencao;

    @Column(name = "con_ds_reduzido", length = 20)
    private String dsReduzido;

    @Column(name = "con_ds_convencao", length = 100)
    private String dsConvencao;

    @Column(name = "con_im_logo", length = 100)
    private String imLogo;

    @Column(name = "con_ds_endereco", length = 100)
    private String dsEndereco;

    @Column(name = "con_ds_bairro", length = 100)
    private String dsBairro;

    @Column(name = "con_ds_cidade", length = 100)
    private String dsCidade;

    // est_sq_estado	int

    @Column(name = "con_ds_pais", length = 50)
    private String dsPais;

    @Column(name = "con_ds_cep", length = 8)
    private String dsCep;

    @Column(name = "con_ds_cnpj", length = 14)
    private String dsCnpj;

    @Column(name = "con_ds_email", length = 100)
    private  String dsEmail;

    @Column(name = "con_ds_telefones", length = 100)
    private String dsTelefones;

    @Column(name = "con_ds_watsapp", length = 50)
    private String dsWatsapp;

    @CreationTimestamp
    @Column(name = "auditoria_data")
    private OffsetDateTime auditoriaData;

    @Column(name = "auditoria_usuario", length = 50)
    private String auditoriaUsuario;
}
