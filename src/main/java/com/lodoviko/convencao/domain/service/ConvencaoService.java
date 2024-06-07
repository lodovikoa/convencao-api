package com.lodoviko.convencao.domain.service;

import com.lodoviko.convencao.domain.exception.RecursoNaoEncontradoException;
import com.lodoviko.convencao.domain.model.Convencao;
import com.lodoviko.convencao.domain.repository.ConvencaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

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

        // Setar auditoria
        convencao.setAuditoriaData(OffsetDateTime.now());
        convencao.setAuditoriaUsuario("Teste");

        return convencaoRepository.save(convencao);
    }

    public Convencao alterar(long sqConvencao, Convencao convencao) {
        // Verificar se a convenção existe no bd
        if(!convencaoRepository.existsById(sqConvencao)) {
            throw new RecursoNaoEncontradoException(String.format("Convenção com ID %d não localizado!", sqConvencao));
        }

        Optional<Convencao> convencaoOptional = convencaoRepository.findById(sqConvencao);
        Convencao convencaoAtual = convencaoOptional.get();


        // Retirar espaços em branco do inicio/fim de cada campo string
        // Fazer validações

        convencao.setSqConvencao(sqConvencao);

        // Setar auditoria
        convencao.setAuditoriaData(OffsetDateTime.now());
        convencao.setAuditoriaUsuario("Teste");
        System.out.println("------------------------ sqConvencao:" + convencaoAtual.getSqConvencao() + " Redzido: " + convencaoAtual.getDsReduzido());
        return convencaoRepository.save(convencao);
    }

    public void excluir(Long sqConvencao) {
        var convencao = this.buscar(sqConvencao);
        convencaoRepository.delete(convencao);
    }
}
