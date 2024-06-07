package com.lodoviko.convencao.api.v1.controller;

import com.lodoviko.convencao.api.v1.openapi.ConvencaoControllerOpenApi;
import com.lodoviko.convencao.domain.model.Convencao;
import com.lodoviko.convencao.domain.service.ConvencaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/convencao/")
public class ConvencaoController implements ConvencaoControllerOpenApi {

    @Autowired
    private ConvencaoService convencaoService;
    @GetMapping
    public List<Convencao> listar() {
        var convencaoList = convencaoService.listar();
        return convencaoList;
    }

    @GetMapping("{sqConvencao}")
    public Convencao buscar (@PathVariable Long sqConvencao) {
        var convencao = convencaoService.buscar(sqConvencao);
        return convencao;
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Convencao cadastrar(@RequestBody @Valid Convencao convencao) {
        convencao = convencaoService.salvar(convencao);
        return convencao;
    }

    @Transactional
    @PutMapping("{sqConvencao}")
    public Convencao alterar(@PathVariable long sqConvencao, @RequestBody @Valid Convencao convencao) {

        convencao = convencaoService.alterar(sqConvencao, convencao);
        return convencao;
    }

    @Transactional
    @DeleteMapping("{sqConvencao}")
    public void excluir(@PathVariable Long sqConvencao) {
        convencaoService.excluir(sqConvencao);
    }
}