package com.lodoviko.convencao.api.v1.controller;

import com.lodoviko.convencao.api.v1.assembler.EstadoDTOAssembler;
import com.lodoviko.convencao.api.v1.assembler.EstadoDTODisassembler;
import com.lodoviko.convencao.api.v1.dto.input.EstadoInputDTO;
import com.lodoviko.convencao.api.v1.dto.model.EstadoDTO;
import com.lodoviko.convencao.api.v1.dto.model.EstadoDTOPage;
import com.lodoviko.convencao.api.v1.dto.model.PaginacaoDTO;
import com.lodoviko.convencao.api.v1.openapi.EstadoControllerOpenApi;
import com.lodoviko.convencao.domain.model.Estado;
import com.lodoviko.convencao.domain.service.EstadoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "v1/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoDTOAssembler estadoDTOAssembler;

    @Autowired
    private EstadoDTODisassembler estadoDTODisassembler;

    @GetMapping
    public ResponseEntity<EstadoDTOPage> listar(@PageableDefault(size = 10) Pageable pageable) {
        log.info(String.format("Listar estados GET v1/estados - paginado: Página numero: %s - qtde elementos na página: %s", pageable.getPageNumber(), pageable.getPageSize()));

        Page estadosPage = estadoService.listar(pageable);
        PaginacaoDTO paginacaoDTO = new PaginacaoDTO(estadosPage.getPageable().getPageSize(), estadosPage.getPageable().getPageNumber(), estadosPage.getTotalPages(), estadosPage.getTotalElements(), estadosPage.isFirst(),estadosPage.isLast());
        List<EstadoDTO> estadosDTO = estadoDTOAssembler.toCollectionModel(estadosPage.getContent());

        return ResponseEntity.ok(new EstadoDTOPage(estadosDTO, paginacaoDTO));
    }

    @GetMapping("/{sqEstado}")
    public ResponseEntity<EstadoDTO> buscar(@PathVariable Long sqEstado) {
        log.info(String.format("Buscar Estado GET v1/estados/{%s}", sqEstado));
        var estado = estadoService.buscar(sqEstado);
        return ResponseEntity.ok(estadoDTOAssembler.toModel(estado));
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EstadoDTO> cadastrar(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
        log.info(String.format("Salvar Estado POST v1/estados/%s", estadoInputDTO.getDsUf()));

        Estado estado = estadoDTODisassembler.toDomainObject(estadoInputDTO);
        estado = estadoService.salvar(estado);
        return ResponseEntity.ok(estadoDTOAssembler.toModel(estado));
    }

    @Transactional
    @PutMapping("/{sqEstado}")
    public ResponseEntity<EstadoDTO> alterar(@PathVariable Long sqEstado, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {
        log.info(String.format("Alterar Estado PUT v1/estados/ID - %s", sqEstado));
        var estadoAtual = estadoService.buscar(sqEstado);

        estadoDTODisassembler.copyToDomainObject(estadoInputDTO, estadoAtual);
        estadoAtual = estadoService.alterar(estadoAtual);
        return ResponseEntity.ok(estadoDTOAssembler.toModel(estadoAtual));
    }

    @Transactional
    @DeleteMapping("/{sqEstado}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long sqEstado) {
        log.info(String.format("Excluir Estado DELETE v1/estados/ID - %s", sqEstado));
        estadoService.excluir(sqEstado);
    }
}
