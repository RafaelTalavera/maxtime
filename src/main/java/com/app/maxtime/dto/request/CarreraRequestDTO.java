package com.app.maxtime.dto.request;



import java.util.Date;
import java.util.List;

public record CarreraRequestDTO(
        String nombre,
        Date fecha,
        Date fechaDeCierreDeInscripcion,
        String localidad,
        String provincia,
        String pais,
        String imagen,
        String detalles,
        String contacto,
        String horario,
        Boolean estado,
        Long organizadorId,
        List<DistanciaRequestDTO> distancias
) {
}
