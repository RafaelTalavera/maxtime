package com.app.maxtime.services;

import com.app.maxtime.dto.request.OrganizadorRequestDTO;
import com.app.maxtime.dto.response.OrganizadorResponseDTO;
import com.app.maxtime.models.entity.Organizador;

import java.util.List;
import java.util.Optional;

public interface IOrganizadorService {

    List<OrganizadorResponseDTO> findAll();

    Optional<Organizador> findById(Long id);

    OrganizadorResponseDTO save(OrganizadorRequestDTO organizadorRequestDTO);

    OrganizadorResponseDTO update(Long id, OrganizadorRequestDTO organizadorRequestDTO);

    void deleteById(Long id);
}
