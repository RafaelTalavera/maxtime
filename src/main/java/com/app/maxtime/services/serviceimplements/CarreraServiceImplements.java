package com.app.maxtime.services.serviceimplements;

import com.app.maxtime.dto.request.CarreraRequestDTO;
import com.app.maxtime.dto.response.CarreraResponseDTO;
import com.app.maxtime.dto.response.DistanciaResponseDTO;
import com.app.maxtime.dto.response.OrganizadorResponseDTO;
import com.app.maxtime.exeption.RegistroNoEncontradoException;
import com.app.maxtime.models.dao.ICarreraDao;
import com.app.maxtime.models.dao.IOrganizadorDao;
import com.app.maxtime.models.entity.Carrera;
import com.app.maxtime.models.entity.Organizador;
import com.app.maxtime.services.ICarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class CarreraServiceImplements implements ICarreraService {

    @Autowired
    private ICarreraDao carreraDao;

    @Autowired
    private IOrganizadorDao organizadorDao;

    @Override
    public List<CarreraResponseDTO> findAll() {
        List<Carrera> carreras = StreamSupport.stream(carreraDao.findAll().spliterator(),false)
                .collect(Collectors.toList());
        return carreras.stream().map(this::convertToDo).collect(Collectors.toList());
    }

    @Override
    public Optional<Carrera> findById(Long id) {
        return carreraDao.findById(id);
    }

    @Override
    @Transactional
    public CarreraResponseDTO createCarrera(CarreraRequestDTO carreraRequestDTO) {
        if (carreraRequestDTO == null) {
            throw new IllegalArgumentException("El objeto CarreraRequestDTO no puede ser nulo");
        }

        // Recuperar el organizador completo desde la base de datos
        Organizador organizador = organizadorDao.findById(carreraRequestDTO.organizadorId())
                .orElseThrow(() -> new RegistroNoEncontradoException("Organizador no encontrado"));

        Carrera carrera = new Carrera(
                null,
                carreraRequestDTO.nombre(),
                carreraRequestDTO.fecha(),
                carreraRequestDTO.fechaDeCierreDeInscripcion(),
                carreraRequestDTO.localidad(),
                carreraRequestDTO.provincia(),
                carreraRequestDTO.pais(),
                carreraRequestDTO.imagen(),
                carreraRequestDTO.detalles(),
                carreraRequestDTO.contacto(),
                carreraRequestDTO.horario(),
                carreraRequestDTO.estado(),
                organizador,
                new ArrayList<>()  // Inicializamos la lista de distancias vacía
        );

        Carrera savedCarrera = carreraDao.save(carrera);

        // Si no hay distancias, retornamos una lista vacía
        List<DistanciaResponseDTO> distanciasResponse = savedCarrera.getDistancias() != null
                ? savedCarrera.getDistancias().stream()
                .map(distancia -> new DistanciaResponseDTO(
                        distancia.getId(),
                        distancia.getTipo(),
                        distancia.getValor(),
                        distancia.getLinkDePago(),
                        distancia.getCarrera().getId(),
                        distancia.getOrganizador().getId()// Añadir el ID de la carrera
                ))
                .collect(Collectors.toList())
                : new ArrayList<>();

        OrganizadorResponseDTO organizadorResponse = new OrganizadorResponseDTO(
                savedCarrera.getOrganizador().getId(),
                savedCarrera.getOrganizador().getNombre(),
                savedCarrera.getOrganizador().getApellido(),
                savedCarrera.getOrganizador().getDni(),
                savedCarrera.getOrganizador().getEmail(),
                savedCarrera.getOrganizador().getTelefono()
        );

        return new CarreraResponseDTO(
                savedCarrera.getId(),
                savedCarrera.getNombre(),
                savedCarrera.getFecha(),
                savedCarrera.getFechaDeCierreDeInscripcion(),
                savedCarrera.getLocalidad(),
                savedCarrera.getProvincia(),
                savedCarrera.getPais(),
                savedCarrera.getImagen(),
                savedCarrera.getDetalles(),
                savedCarrera.getContacto(),
                savedCarrera.getHorario(),
                savedCarrera.getEstado(),
                organizadorResponse,
                distanciasResponse
        );
    }

    @Override
    public CarreraResponseDTO update(Long id, CarreraRequestDTO carreraRequestDTO) {
        Carrera existingCarrera = carreraDao.findById(id).orElseThrow(() ->
        new RuntimeException("No se encontró ningún registro con el ID: " + id));

        existingCarrera.setNombre(carreraRequestDTO.nombre());
        existingCarrera.setFecha(carreraRequestDTO.fecha());
        existingCarrera.setFechaDeCierreDeInscripcion(carreraRequestDTO.fechaDeCierreDeInscripcion());
        existingCarrera.setLocalidad(carreraRequestDTO.localidad());
        existingCarrera.setProvincia(carreraRequestDTO.provincia());
        existingCarrera.setPais(carreraRequestDTO.pais());
        existingCarrera.setImagen(carreraRequestDTO.imagen());
        existingCarrera.setContacto(carreraRequestDTO.contacto());
        existingCarrera.setHorario(carreraRequestDTO.horario());
        existingCarrera.setEstado(carreraRequestDTO.estado());

        Carrera updatedCarrera = carreraDao.save(existingCarrera);
        return convertToDo(updatedCarrera);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Carrera existingCarrera = carreraDao.findById(id).orElseThrow(() ->
                new RegistroNoEncontradoException("No se encontró ningún registro con el ID: " + id));
        carreraDao.deleteById(id);
    }

    @Override
    public List<CarreraResponseDTO> findByOrganizadorId(Long organizadorId) {
        // Buscar las carreras asociadas al organizador con el ID proporcionado
        List<Carrera> carreras = carreraDao.findByOrganizadorId(organizadorId);

        // Convertir las carreras encontradas a sus DTOs correspondientes
        return carreras.stream()
                .map(this::convertToDo)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarreraResponseDTO> findActiveCarreras() {  // Nuevo método para obtener carreras activas
        List<Carrera> carreras = carreraDao.findByEstadoTrue();
        return carreras.stream().map(this::convertToDo).collect(Collectors.toList());
    }


    private CarreraResponseDTO convertToDo(Carrera carrera) {
        // Convertir el organizador a su DTO correspondiente
        OrganizadorResponseDTO organizadorResponse = new OrganizadorResponseDTO(
                carrera.getOrganizador().getId(),
                carrera.getOrganizador().getNombre(),
                carrera.getOrganizador().getApellido(),
                carrera.getOrganizador().getDni(),
                carrera.getOrganizador().getEmail(),
                carrera.getOrganizador().getTelefono()
        );

        // Convertir las distancias a sus DTOs correspondientes
        List<DistanciaResponseDTO> distanciasResponse = carrera.getDistancias().stream()
                .map(distancia -> new DistanciaResponseDTO(
                        distancia.getId(),
                        distancia.getTipo(),
                        distancia.getValor(),
                        distancia.getLinkDePago(),
                        distancia.getCarrera().getId(),
                        distancia.getOrganizador().getId())) // Incluir el ID de la carrera
                .collect(Collectors.toList());

        // Retornar el CarreraResponseDTO completo
        return new CarreraResponseDTO(
                carrera.getId(),
                carrera.getNombre(),
                carrera.getFecha(),
                carrera.getFechaDeCierreDeInscripcion(),
                carrera.getLocalidad(),
                carrera.getProvincia(),
                carrera.getPais(),
                carrera.getImagen(),
                carrera.getDetalles(),
                carrera.getContacto(),
                carrera.getHorario(),
                carrera.getEstado(),
                organizadorResponse,
                distanciasResponse
        );
    }

}
