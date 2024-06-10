package com.app.maxtime.dto.request;

public record AuthenticationRequestDTO(
        String username,
        String password
) {
}
