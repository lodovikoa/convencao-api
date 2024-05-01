package com.lodoviko.convencao.api.v1.assembler;

import com.lodoviko.convencao.api.v1.controller.EstadoController;
import com.lodoviko.convencao.api.v1.dto.model.EstadoDTO;
import com.lodoviko.convencao.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

    @Autowired
    private ModelMapper modelmapper;

    public EstadoDTOAssembler() {
        super(EstadoController.class, EstadoDTO.class);
    }

    @Override
    public EstadoDTO toModel(Estado estado) {
        var estadoDto = createModelWithId(estado.getSqEstado(), estado);
        modelmapper.map(estado,estadoDto);
        return estadoDto;
    }

    @Override
    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> estados) {
        var colletionModel = super.toCollectionModel(estados);
        return super.toCollectionModel(estados);
    }
}
