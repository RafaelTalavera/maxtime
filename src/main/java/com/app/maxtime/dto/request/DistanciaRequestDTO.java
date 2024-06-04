package com.app.maxtime.dto.request;


import java.math.BigDecimal;

public record DistanciaRequestDTO(
        Long id,
        String tipo,
        BigDecimal valor,
        String linkDePago,
        Long carreraId,
        Long organizadorId
) {
}
