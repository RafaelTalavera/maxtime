package com.app.maxtime.dto.response;

public record OrganizadorResponseDTO(
        Long id,
        String nombre,
        String apellido,
        String dni,
        String email,
        String telefono
) {
}
