package com.lodoviko.convencao.domain.service;

import com.lodoviko.convencao.domain.exception.RecursoJaCadastradoException;
import com.lodoviko.convencao.domain.exception.RecursoNaoEncontradoException;
import com.lodoviko.convencao.domain.model.Convencao;
import com.lodoviko.convencao.domain.repository.ConvencaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ConvencaoService {

    @Autowired
    private ConvencaoRepository convencaoRepository;

    public List<Convencao> listar() {
        return convencaoRepository.findAll();
    }

    public Convencao buscar(Long sqConvencao) {
        return convencaoRepository.findById(sqConvencao).orElseThrow(() -> new RecursoNaoEncontradoException(Convencao.class.getSimpleName(), sqConvencao));
    }

    public Convencao salvar(Convencao convencao) {

        // Retirar espaços em branco no ínicio e fim de cada campo string
        // Fazer validações
        if(convencaoRepository.existsByDsReduzidoAndSqConvencaoNot(convencao.getDsReduzido(), 0L)) {
            throw new RecursoJaCadastradoException(String.format("Convenção com nome reduzido %s já encontra-se cadastrado", convencao.getDsReduzido()));
        }

        if(convencaoRepository.existsByDsConvencaoAndSqConvencaoNot(convencao.getDsConvencao(), 0L)) {
            throw new RecursoJaCadastradoException(String.format("Convenção com nome %s já encontra-se cadastrado", convencao.getDsConvencao()));
        }

        // Setar auditoria
        convencao.setAuditoriaData(OffsetDateTime.now());
        convencao.setAuditoriaUsuario("Teste");

        return convencaoRepository.save(convencao);
    }

    public Convencao alterar(long sqConvencao, Convencao convencao) {
    // Buscar a convenção atual, caso não exista o método buscar irá lançar erro
        Convencao convencaoAtual = this.buscar(sqConvencao);

        // Retirar espaços em branco do inicio/fim de cada campo string
        // Fazer validações
        if(convencaoRepository.existsByDsReduzidoAndSqConvencaoNot(convencao.getDsReduzido(), sqConvencao)) {
            throw new RecursoJaCadastradoException(String.format("Convenção com nome reduzido %s já encontra-se cadastrado", convencao.getDsReduzido()));
        }

        if(convencaoRepository.existsByDsConvencaoAndSqConvencaoNot(convencao.getDsConvencao(), sqConvencao)) {
            throw new RecursoJaCadastradoException(String.format("Convenção com nome %s já encontra-se cadastrado", convencao.getDsConvencao()));
        }

        convencao.setSqConvencao(sqConvencao);

        // Setar auditoria
        convencao.setAuditoriaData(OffsetDateTime.now());
        convencao.setAuditoriaUsuario("Teste");

        return convencaoRepository.save(convencao);
    }

    public void excluir(Long sqConvencao) {
        var convencao = this.buscar(sqConvencao);
        convencaoRepository.delete(convencao);
    }
}
