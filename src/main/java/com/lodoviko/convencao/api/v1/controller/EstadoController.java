package com.lodoviko.convencao.api.v1.controller;

import com.lodoviko.convencao.api.v1.assembler.EstadoDTOAssembler;
import com.lodoviko.convencao.api.v1.assembler.EstadoDTODisassembler;
import com.lodoviko.convencao.api.v1.dto.model.EstadoDTO;
import com.lodoviko.convencao.api.v1.dto.input.EstadoInputDTO;
import com.lodoviko.convencao.api.v1.openapi.EstadoControllerOpenApi;
import com.lodoviko.convencao.domain.model.Estado;
import com.lodoviko.convencao.domain.service.EstadoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("v1/estados")
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoDTOAssembler estadoDTOAssembler;

    @Autowired
    private EstadoDTODisassembler estadoDTODisassembler;

    @Autowired
    private PagedResourcesAssembler<Estado> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<EstadoDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
        log.info("Listar estados GET v1/estados - página atual {}", pageable.getPageNumber());

        Page<Estado> estadosPage = estadoService.listar(pageable);

        return pagedResourcesAssembler.toModel(estadosPage, estadoDTOAssembler);
    }

    @GetMapping("/{sqEstado}")
    public EstadoDTO buscar(@PathVariable Long sqEstado) {
        log.info("Buscar Estado GET v1/estados/{}", sqEstado);
        var estado = estadoService.buscar(sqEstado);
        return estadoDTOAssembler.toModel(estado);
    }


    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO incluir(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
        log.info(String.format("Salvar Estado POST v1/estados - %s", estadoInputDTO.getDsUf()));

        Estado estado = estadoDTODisassembler.toDomainObject(estadoInputDTO);
        estado = estadoService.salvar(estado);
        return estadoDTOAssembler.toModel(estado);
    }

    @Transactional
    @PutMapping("{sqEstado}")
    public EstadoDTO alterar(@PathVariable Long sqEstado, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {
        log.info(String.format("Alterar Estado PUT v1/estados/ID - %s", sqEstado));
        var estadoAtual = estadoService.buscar(sqEstado);

        estadoDTODisassembler.copyToDomainObject(estadoInputDTO, estadoAtual);
        estadoAtual = estadoService.alterar(estadoAtual);
        return estadoDTOAssembler.toModel(estadoAtual);
    }

    @Transactional
    @DeleteMapping("{sqEstado}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long sqEstado) {
        log.info(String.format("Excluir Estado DELETE v1/estados/ID - %s", sqEstado));
        estadoService.excluir(sqEstado);
    }
}
