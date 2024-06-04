package com.app.maxtime.dto.request;

public record OrganizadorRequestDTO(
        String nombre,
        String apellido,
        String dni,
        String email,
        String telefono
) {
}
