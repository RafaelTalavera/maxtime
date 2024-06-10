package com.app.maxtime.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;
import java.util.List;

public record CarreraResponseDTO(
        Long id,
        String nombre,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date fecha,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date fechaDeCierreDeInscripcion,
        String localidad,
        String provincia,
        String pais,
        String imagen,
        String detalles,
        String contacto,
        String horario,
        Boolean estado,
        UserResponseDTO user,
        List<DistanciaResponseDTO> distancias
) {
}
