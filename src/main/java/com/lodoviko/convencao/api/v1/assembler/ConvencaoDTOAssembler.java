package com.lodoviko.convencao.api.v1.assembler;

import com.lodoviko.convencao.api.v1.dto.model.ConvencaoDTO;
import com.lodoviko.convencao.domain.model.Convencao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConvencaoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ConvencaoDTO toModel(Convencao convencao) {
        return modelMapper.map(convencao, ConvencaoDTO.class);
    }

    public List<ConvencaoDTO> toCollectionMode(List<Convencao> convencaos) {
        return convencaos
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
