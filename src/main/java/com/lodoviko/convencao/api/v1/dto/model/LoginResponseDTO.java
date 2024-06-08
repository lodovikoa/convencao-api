package com.lodoviko.convencao.api.v1.dto.model;

public record LoginResponseDTO(
        String token,
        String dsLogin,
        String dsNome,
        String trancodes
) { }
