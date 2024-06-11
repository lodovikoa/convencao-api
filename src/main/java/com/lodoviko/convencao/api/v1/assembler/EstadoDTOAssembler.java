package com.lodoviko.convencao.api.v1.assembler;

import com.lodoviko.convencao.api.v1.dto.model.EstadoDTO;
import com.lodoviko.convencao.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoDTOAssembler {

    @Autowired
    private ModelMapper modelmapper;


    public EstadoDTO toModel(Estado estado) {
       return modelmapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> toCollectionModel(List<Estado> estados) {
        return estados
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

//    @Override
//    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> estados) {
//        var colletionModel = super.toCollectionModel(estados);
//
//        return colletionModel;
//    }
//
//    public EstadoDTO toCollectionModel2(Estado estado) {
//        return modelmapper.map(estado, EstadoDTO.class);
//    }
}
