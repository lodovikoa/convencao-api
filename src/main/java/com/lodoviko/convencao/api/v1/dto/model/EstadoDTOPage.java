package com.lodoviko.convencao.api.v1.dto.model;

import java.util.List;

public record EstadoDTOPage(List<EstadoDTO> content, PaginacaoDTO pageable) {
}
