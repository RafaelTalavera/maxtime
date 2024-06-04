package com.app.maxtime.services;

import com.app.maxtime.dto.request.CarreraRequestDTO;
import com.app.maxtime.dto.response.CarreraResponseDTO;
import com.app.maxtime.models.entity.Carrera;


import java.util.List;
import java.util.Optional;

public interface ICarreraService {

    public List<CarreraResponseDTO> findAll();

    public Optional<Carrera> findById(Long id);

    public CarreraResponseDTO createCarrera(CarreraRequestDTO carreraRequestDTO);

    CarreraResponseDTO update(Long id, CarreraRequestDTO carreraRequestDTO);

    public void deleteById(Long id);

    List<CarreraResponseDTO> findByOrganizadorId(Long organizadorId);

    List<CarreraResponseDTO> findActiveCarreras();

}
