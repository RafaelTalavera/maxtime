package com.app.maxtime.services.serviceimplements;

import com.app.maxtime.dto.request.OrganizadorRequestDTO;
import com.app.maxtime.dto.response.OrganizadorResponseDTO;
import com.app.maxtime.models.dao.IOrganizadorDao;
import com.app.maxtime.models.entity.Organizador;
import com.app.maxtime.services.IOrganizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrganizadorServiceImplemts implements IOrganizadorService {

    @Autowired
    private IOrganizadorDao organizadorDao;


    @Override
    public List<OrganizadorResponseDTO> findAll() {
        List<Organizador> organizadores = StreamSupport.stream(organizadorDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return organizadores.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Organizador> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public OrganizadorResponseDTO save(OrganizadorRequestDTO organizadorRequestDTO) {
        Organizador organizador = new Organizador(
                null,
                organizadorRequestDTO.nombre(),
                organizadorRequestDTO.apellido(),
                organizadorRequestDTO.dni(),
                organizadorRequestDTO.email(),
                organizadorRequestDTO.telefono(),
                null
        );
        Organizador savedOrganizador = organizadorDao.save(organizador);
        return new OrganizadorResponseDTO(
                savedOrganizador.getId(),
                savedOrganizador.getNombre(),
                savedOrganizador.getApellido(),
                savedOrganizador.getDni(),
                savedOrganizador.getEmail(),
                savedOrganizador.getTelefono()
        );
    }

    @Override
    public OrganizadorResponseDTO update(Long id, OrganizadorRequestDTO organizadorRequestDTO) {
        Organizador existingOrganizador = organizadorDao.findById(id).orElseThrow(() ->
                new RuntimeException("No se encontró ningún registro con el ID: " + id));

        existingOrganizador.setNombre(organizadorRequestDTO.nombre());
        existingOrganizador.setApellido(organizadorRequestDTO.apellido());
        existingOrganizador.setDni(organizadorRequestDTO.dni());
        existingOrganizador.setEmail(organizadorRequestDTO.email());
        existingOrganizador.setTelefono(organizadorRequestDTO.telefono());

        Organizador updatedOrganizador = organizadorDao.save(existingOrganizador);
        return convertToDto(updatedOrganizador);
    }

    @Override
    public void deleteById(Long id) {
        Organizador existOrganizador = organizadorDao.findById(id).orElseThrow(() ->
                new RuntimeException("No se encontró ningún registro con el ID: " + id));
        organizadorDao.deleteById(id);
    }

    private OrganizadorResponseDTO convertToDto(Organizador organizador) {
        return new OrganizadorResponseDTO(
                organizador.getId(),
                organizador.getNombre(),
                organizador.getApellido(),
                organizador.getDni(),
                organizador.getEmail(),
                organizador.getTelefono()
        );
    }
}
