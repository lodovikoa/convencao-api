package com.lodoviko.convencao.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_usu_usuario")
public class Usuario implements UserDetails {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_sq_usuario")
    private Long sqUsuario;

    @Column(name = "usu_ds_nome", length = 50)
    private String dsNome;

    @Column(name = "usu_ds_login", length = 15)
    private String dsLogin;

    @Column(name = "usu_ds_senha", length = 60)
    private String dsSenha;

    @Column(name = "usu_ds_email", length = 100)
    private String dsEmail;

    @Column(name = "usu_dt_cadastro")
    private LocalDate dtCadastro;

    @Column(name = "usu_is_situacao")
    private boolean isSituacao;

    @Column(name = "usu_ds_obs")
    private String usu_ds_obs;

    @Column(name = "usu_is_troca_senha")
    private boolean isTrocaSenha;

    @Column(name = "usu_nn_quantidade_erros")
    private Integer nnQuantidadeErros;

    @CreationTimestamp
    @Column(name = "auditoria_data")
    private OffsetDateTime auditoriaData;

    @Column(name = "auditoria_usuario", length = 50)
    private String auditoriaUsuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_guCftAdministrador"), new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.getDsSenha();
    }

    @Override
    public String getUsername() {
        return this.dsLogin;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
