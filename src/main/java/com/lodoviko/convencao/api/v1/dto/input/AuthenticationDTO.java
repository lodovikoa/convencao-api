package com.lodoviko.convencao.api.v1.dto.input;

public record AuthenticationDTO(
        String login,
        String password
) {
}
