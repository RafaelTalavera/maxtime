package com.app.maxtime.services.serviceimplements;

import com.app.maxtime.dto.request.DistanciaRequestDTO;
import com.app.maxtime.dto.response.DistanciaResponseDTO;
import com.app.maxtime.exeption.RegistroNoEncontradoException;
import com.app.maxtime.models.dao.IDistanciaDao;
import com.app.maxtime.models.dao.ICarreraDao;
import com.app.maxtime.models.dao.IOrganizadorDao;
import com.app.maxtime.models.entity.Carrera;
import com.app.maxtime.models.entity.Distancia;

import com.app.maxtime.models.entity.Organizador;
import com.app.maxtime.services.IDistanciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DistanciaServiceImplements implements IDistanciaService {

    @Autowired
    private IDistanciaDao distanciaDao;

    @Autowired
    private ICarreraDao carreraDao;

    @Autowired
    private IOrganizadorDao organizadorDao;

    @Override
    public List<DistanciaResponseDTO> getAllDistancias() {
        List<Distancia> distancias = new ArrayList<>();
        distanciaDao.findAll().forEach(distancias::add);
        return distancias.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DistanciaResponseDTO getDistanciaById(Long id) {
        Distancia distancia = distanciaDao.findById(id).orElseThrow(() ->
                new RegistroNoEncontradoException("No se encontró ningún registro con el ID: " + id));
        return convertToDto(distancia);
    }



    @Override
    @Transactional
    public DistanciaResponseDTO createDistancia(DistanciaRequestDTO distanciaRequestDTO) {
        Carrera carrera = carreraDao.findById(distanciaRequestDTO.carreraId())
                .orElseThrow(() -> new RegistroNoEncontradoException("Carrera no encontrada con el ID: " + distanciaRequestDTO.carreraId()));

        Organizador organizador = organizadorDao.findById(distanciaRequestDTO.organizadorId())
                .orElseThrow(() -> new RegistroNoEncontradoException("Organizador no encontrado con el ID: " + distanciaRequestDTO.organizadorId()));

        Distancia nuevaDistancia = new Distancia(
                null,
                distanciaRequestDTO.tipo(),
                distanciaRequestDTO.valor(),
                distanciaRequestDTO.linkDePago(),
                carrera,
                organizador,  // Establecer el organizador aquí
                new ArrayList<>() // Lista de corredores vacía por defecto
        );

        Distancia savedDistancia = distanciaDao.save(nuevaDistancia);
        return convertToDto(savedDistancia);
    }


    @Override
    @Transactional
    public DistanciaResponseDTO updateDistancia(Long id, DistanciaRequestDTO distanciaRequestDTO) {
        Distancia distanciaExistente = distanciaDao.findById(id).orElseThrow(() ->
                new RegistroNoEncontradoException("No se encontró ningún registro con el ID: " + id));

        distanciaExistente.setTipo(distanciaRequestDTO.tipo());
        distanciaExistente.setValor(distanciaRequestDTO.valor());
        distanciaExistente.setLinkDePago(distanciaRequestDTO.linkDePago());

        Distancia updatedDistancia = distanciaDao.save(distanciaExistente);
        return convertToDto(updatedDistancia);
    }

    @Override
    @Transactional
    public void deleteDistancia(Long id) {
        Distancia existDistancia = distanciaDao.findById(id).orElseThrow(() ->
                new RegistroNoEncontradoException("No se encontró ningún registro con el ID: " + id));
        distanciaDao.deleteById(id);
    }

    @Override
    public Optional<Distancia> findById(Long id) {
        return distanciaDao.findById(id);
    }

    @Override
    public List<DistanciaResponseDTO> findByOrganizadorIdAndCarreraId(Long organizadorId, Long carreraId) {
        List<Distancia> distancias = distanciaDao.findByOrganizadorIdAndCarreraId(organizadorId, carreraId);
        return distancias.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    private DistanciaResponseDTO convertToDto(Distancia distancia) {
        return new DistanciaResponseDTO(
                distancia.getId(),
                distancia.getTipo(),
                distancia.getValor(),
                distancia.getLinkDePago(),
                distancia.getCarrera().getId(),
                distancia.getOrganizador().getId()

        );
    }
}
