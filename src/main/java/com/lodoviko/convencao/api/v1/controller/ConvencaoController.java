package com.lodoviko.convencao.api.v1.controller;

import com.lodoviko.convencao.api.v1.assembler.ConvencaoDTOAssembler;
import com.lodoviko.convencao.api.v1.dto.model.ConvencaoDTO;
import com.lodoviko.convencao.api.v1.openapi.ConvencaoControllerOpenApi;
import com.lodoviko.convencao.domain.model.Convencao;
import com.lodoviko.convencao.domain.service.ConvencaoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/convencao")
public class ConvencaoController implements ConvencaoControllerOpenApi {

    @Autowired
    private ConvencaoService convencaoService;
    @Autowired
    private ConvencaoDTOAssembler convencaoDTOAssembler;


    @GetMapping
    public ResponseEntity<List<ConvencaoDTO>> listar() {
        log.info(String.format("Listar convenção GET/ v1/convencao"));
        var convencaoList = convencaoService.listar();
        return ResponseEntity.ok(convencaoDTOAssembler.toCollectionMode(convencaoList));
    }

    @GetMapping("/{sqConvencao}")
    public ResponseEntity<ConvencaoDTO> buscar (@PathVariable Long sqConvencao) {
        log.info(String.format("Buscar uma convenção GET/ v1/convencao/%s", sqConvencao));
        var convencao = convencaoService.buscar(sqConvencao);
        return ResponseEntity.ok(convencaoDTOAssembler.toModel(convencao));
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ConvencaoDTO> cadastrar(@RequestBody @Valid Convencao convencao) {
        log.info(String.format("Cadastrar uma convenção POST/ v1/convencao - Nome reduzido: %s - Nome da convenção: %s", convencao.getDsReduzido(), convencao.getDsConvencao()));
        convencao = convencaoService.salvar(convencao);
        return ResponseEntity.ok(convencaoDTOAssembler.toModel(convencao));
    }

    @Transactional
    @PutMapping("/{sqConvencao}")
    public ResponseEntity<ConvencaoDTO> alterar(@PathVariable long sqConvencao, @RequestBody @Valid Convencao convencao) {
        log.info(String.format("Alterar uma convenção PUT/ v1/convencao/%s - Nome reduzido: %s - Nome da convenção: %s", sqConvencao, convencao.getDsReduzido(), convencao.getDsConvencao()));
        convencao = convencaoService.alterar(sqConvencao, convencao);
        return ResponseEntity.ok(convencaoDTOAssembler.toModel(convencao));
    }

    @Transactional
    @DeleteMapping("/{sqConvencao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long sqConvencao) {
        log.info(String.format("Excluir uma convenção DELETE/ v1/convencao/%s", sqConvencao));
        convencaoService.excluir(sqConvencao);
    }
}