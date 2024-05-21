package com.lodoviko.convencao.domain.service;

import com.lodoviko.convencao.domain.exception.RecursoBloqueadoException;
import com.lodoviko.convencao.domain.exception.RecursoNaoEncontradoException;
import com.lodoviko.convencao.domain.model.Usuario;
import com.lodoviko.convencao.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnvioEmailService envioEmailService;

    public void recuperarSenha(String dsLogin) {

        Usuario usuario = usuarioRepository.findPorLogin(dsLogin);

        if(usuario == null) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado.");
        } else if(!usuario.isSituacao()) {
            throw new RecursoBloqueadoException("Usuário bloqueado.");
        } else if(usuario.getDsEmail().trim().length() == 0) {
            throw new RecursoNaoEncontradoException("Não há e-mail cadastrado.");
        }

        // Criar nova senha e envia-la por e-mail
        String senhaNova = UUID.randomUUID().toString();
        senhaNova = senhaNova.substring(0,6);

        System.out.println("Nova senha: " + senhaNova);

        String senhaCript = new BCryptPasswordEncoder().encode(senhaNova);

        usuario.setDsSenha(senhaCript);
        usuario.setTrocaSenha(true);
        usuario.setAuditoriaData(OffsetDateTime.now());
        usuario.setAuditoriaUsuario(usuario.getDsLogin());

        usuarioRepository.save(usuario);

        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto("CONFRATERES - Recuperação de senha")
                .corpo("recuperar-senha.html")
                .variavel("dsSenha", senhaNova)
                .destinatario(usuario.getDsEmail())
                .build();

        envioEmailService.enivar(mensagem);
    }
}
