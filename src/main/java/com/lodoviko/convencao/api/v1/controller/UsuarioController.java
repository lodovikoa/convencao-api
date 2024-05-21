package com.lodoviko.convencao.api.v1.controller;

import com.lodoviko.convencao.api.v1.dto.input.UsuarioRecuperarSenhaDTO;
import com.lodoviko.convencao.api.v1.dto.model.UsuarioRecuperarSenhaResponseDTO;
import com.lodoviko.convencao.domain.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    @PostMapping("/recuperarSenha")
    public UsuarioRecuperarSenhaResponseDTO recuperarSenha(@RequestBody UsuarioRecuperarSenhaDTO dsLogin) {
        this.usuarioService.recuperarSenha(dsLogin.dsLogin());
        return new  UsuarioRecuperarSenhaResponseDTO("Senha enviada para o email cadastrado.");
    }
}
