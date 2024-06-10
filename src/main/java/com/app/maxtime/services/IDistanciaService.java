package com.app.maxtime.services;

import com.app.maxtime.dto.request.DistanciaRequestDTO;
import com.app.maxtime.dto.response.DistanciaResponseDTO;
import com.app.maxtime.models.entity.Distancia;

import java.util.List;
import java.util.Optional;

public interface IDistanciaService {

    List<DistanciaResponseDTO> getAllDistancias();

    DistanciaResponseDTO getDistanciaById(Long id);

    DistanciaResponseDTO createDistancia(DistanciaRequestDTO distanciaRequestDTO);

    DistanciaResponseDTO updateDistancia(Long id, DistanciaRequestDTO distanciaRequestDTO);

    void deleteDistancia(Long id);

    Optional<Distancia> findById(Long id) ;

    List<DistanciaResponseDTO> findByUserIdAndCarreraId(Long organizadorId, Long distanciaId);
}
