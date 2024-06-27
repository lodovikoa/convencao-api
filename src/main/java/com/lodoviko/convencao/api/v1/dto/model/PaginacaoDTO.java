package com.lodoviko.convencao.api.v1.dto.model;

public record PaginacaoDTO(
       int pageSize,
       int pageNumber,
       int totalPages,
       long totalElements,
       boolean firstPage,
       boolean lastPage

) {
}
