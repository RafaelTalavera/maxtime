package com.app.maxtime.dto.response;


import java.math.BigDecimal;

public record DistanciaResponseDTO(
        Long id,
        String tipo,
        BigDecimal valor,
        String linkDePago,
        Long carreraId,
        Long organizadorId
) {

}

