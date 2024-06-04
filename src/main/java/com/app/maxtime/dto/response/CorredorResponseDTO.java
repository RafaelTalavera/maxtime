package com.app.maxtime.dto.response;

public record CorredorResponseDTO(
        Long id,
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
        Boolean confirmado,
        CarreraResponseDTO carrera,
        DistanciaResponseDTO distancia
) {
}
