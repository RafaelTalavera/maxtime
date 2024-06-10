package com.app.maxtime.dto.request;

public record CorredorRequestDTO(
        String nombre,
        String apellido,
        String dni,
        String fechaNacimiento,
        String genero,
        String nacionalidad,
        String provincia,
        String localidad,
        String talle,
        String telefono,
        String email,
        String team,
        String grupoSanguinio,
        boolean confirmado,
        Long carreraId,
        Long distanciaId
) {
}
