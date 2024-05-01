package com.lodoviko.convencao.domain.service;

import com.lodoviko.convencao.domain.exception.RecursoJaCadastradoException;
import com.lodoviko.convencao.domain.exception.RecursoNaoEncontradoException;
import com.lodoviko.convencao.domain.model.Estado;
import com.lodoviko.convencao.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Page<Estado> listar(Pageable pageable) {
        return estadoRepository.findAll(pageable);
    }

    public Estado buscar(Long sqEstado) {
        return estadoRepository.findById(sqEstado).orElseThrow(() -> new RecursoNaoEncontradoException(Estado.class.getSimpleName(), sqEstado));
    }

    public Estado salvar(Estado estado) {
        if (estado.getDsUf() != null) estado.setDsUf(estado.getDsUf().toUpperCase().trim());
        if (estado.getDsNome() != null) estado.setDsNome(estado.getDsNome().trim());

        if(estadoRepository.existsByDsUf(estado.getDsUf())) {
            throw new RecursoJaCadastradoException(String.format("Estado com UF %s já encontra-se cadastrado", estado.getDsUf()));
        }

        if(estadoRepository.existsByDsNome(estado.getDsNome())) {
            throw new RecursoJaCadastradoException(String.format("Estado com Nome %s já encontra-se cadastrado", estado.getDsNome()));
        }

        estado.setAuditoriaData(OffsetDateTime.now());
        estado.setAuditoriaUsuario("Teste");

        return estadoRepository.save(estado);
    }
    
    public Estado alterar(Estado estado) {
        if (estado.getDsUf() != null) estado.setDsUf(estado.getDsUf().toUpperCase().trim());
        if (estado.getDsNome() != null) estado.setDsNome(estado.getDsNome().trim());

        if(estadoRepository.existsByDsUfAndSqEstadoNot(estado.getDsUf(), estado.getSqEstado())) {
            throw new RecursoJaCadastradoException(String.format("Estado com UF %s já encontra-se cadastrado", estado.getDsUf()));
        }

        if(estadoRepository.existsByDsNomeAndSqEstadoNot(estado.getDsNome(), estado.getSqEstado())) {
            throw new RecursoJaCadastradoException(String.format("Estado com Nome %s já encontra-se cadastrado", estado.getDsNome()));
        }

        estado.setAuditoriaData(OffsetDateTime.now());
        estado.setAuditoriaUsuario("Teste");

        return estadoRepository.save(estado);
    }

    public void excluir(Long sqEstado) {
        var estado = this.buscar(sqEstado);

        estadoRepository.delete(estado);
    }
}
