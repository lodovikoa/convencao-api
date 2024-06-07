package com.lodoviko.convencao.api.v1.dto.model;

import java.util.List;

public record LoginResponseDTO(
        String token,
        String dsLogin,
        String dsNome,
        List<String> trancodes
) { }
