package com.app.maxtime.controllers;

import com.app.maxtime.dto.request.CorredorRequestDTO;
import com.app.maxtime.dto.response.CarreraResponseDTO;
import com.app.maxtime.dto.response.CorredorResponseDTO;
import com.app.maxtime.dto.response.DistanciaResponseDTO;
import com.app.maxtime.mapper.CorredorMapper;
import com.app.maxtime.models.entity.Carrera;
import com.app.maxtime.models.entity.Corredor;
import com.app.maxtime.models.entity.Distancia;
import com.app.maxtime.services.ICarreraService;
import com.app.maxtime.services.ICorredorService;
import com.app.maxtime.services.IDistanciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/corredores")
@CrossOrigin(origins = "http://localhost:4200")
public class CorredorController {

    @Autowired
    private ICorredorService corredorService;

    @Autowired
    private IDistanciaService distanciaService;

    @Autowired
    private ICarreraService carreraService;

    @GetMapping("/carrera/{dni}")
    public ResponseEntity<List<CorredorResponseDTO>> getCorredoresByCarreraId(@PathVariable String dni) {
        List<Corredor> corredores = corredorService.findByDni(dni);
        // Convierte los Corredores en CorredorResponseDTO
        List<CorredorResponseDTO> corredoresResponse = convertToCorredorResponseDTO(corredores);
        return ResponseEntity.ok(corredoresResponse);
    }

    @PostMapping
    public ResponseEntity<CorredorResponseDTO> createCorredor(@RequestBody CorredorRequestDTO corredorRequestDTO) {
        // Crear un nuevo corredor
        Corredor corredor = new Corredor();

        // Establecer los atributos del corredor desde el DTO
        corredor.setNombre(corredorRequestDTO.nombre());
        corredor.setApellido(corredorRequestDTO.apellido());
        corredor.setDni(corredorRequestDTO.dni());
        corredor.setFechaNacimiento(corredorRequestDTO.fechaNacimiento());
        corredor.setGenero(corredorRequestDTO.genero());
        corredor.setNacionalidad(corredorRequestDTO.nacionalidad());
        corredor.setProvincia(corredorRequestDTO.provincia());
        corredor.setLocalidad(corredorRequestDTO.localidad());
        corredor.setTalle(corredorRequestDTO.talle());
        corredor.setTelefono(corredorRequestDTO.telefono());
        corredor.setEmail(corredorRequestDTO.email());
        corredor.setTeam(corredorRequestDTO.team());
        corredor.setGrupoSanguinio(corredorRequestDTO.grupoSanguinio());

        // Obtener la carrera y la distancia de la base de datos utilizando los ID proporcionados en el DTO
        Carrera carrera = carreraService.findById(corredorRequestDTO.carreraId())
                .orElseThrow(() -> new RuntimeException("No se encontró la carrera con el ID: " + corredorRequestDTO.carreraId()));

        Distancia distancia = distanciaService.findById(corredorRequestDTO.distanciaId())
                .orElseThrow(() -> new RuntimeException("No se encontró la distancia con el ID: " + corredorRequestDTO.distanciaId()));

        // Asignar la carrera y la distancia al corredor
        corredor.setCarrera(carrera);
        corredor.setDistancia(distancia);

        // Guardar el corredor en la base de datos
        Corredor savedCorredor = corredorService.save(corredor);

        // Convertir el corredor guardado a DTO y devolverlo en el ResponseEntity
        CorredorResponseDTO corredorResponseDTO = CorredorMapper.toDTO(savedCorredor);
        return ResponseEntity.ok(corredorResponseDTO);
    }

    private List<CorredorResponseDTO> convertToCorredorResponseDTO(List<Corredor> corredores) {
        return corredores.stream().map(this::mapToCorredorResponseDTO).collect(Collectors.toList());
    }
    private CorredorResponseDTO mapToCorredorResponseDTO(Corredor corredor) {
        // Obtener la carrera y la distancia asociadas al corredor
        Carrera carrera = corredor.getCarrera();
        Distancia distancia = corredor.getDistancia();

        // Crear CarreraResponseDTO con solo el ID y el nombre
        CarreraResponseDTO carreraResponseDTO = new CarreraResponseDTO(carrera.getId(), carrera.getNombre(),
                carrera.getFecha(), carrera.getFechaDeCierreDeInscripcion(), carrera.getLocalidad(), carrera.getProvincia(),
                carrera.getPais(), carrera.getImagen(), carrera.getDetalles(), carrera.getContacto(), carrera.getHorario(),
                carrera.getEstado(), null, null);

        // Crear DistanciaResponseDTO con solo el ID y el tipo
        DistanciaResponseDTO distanciaResponseDTO = new DistanciaResponseDTO(distancia.getId(), distancia.getTipo(),
                distancia.getValor(), distancia.getLinkDePago(), null, null);

        // Crear CorredorResponseDTO
        return new CorredorResponseDTO(
                corredor.getId(),
                corredor.getNombre(),
                corredor.getApellido(),
                corredor.getDni(),
                corredor.getFechaNacimiento(),
                corredor.getGenero(),
                corredor.getNacionalidad(),
                corredor.getProvincia(),
                corredor.getLocalidad(),
                corredor.getTalle(),
                corredor.getTelefono(),
                corredor.getEmail(),
                corredor.getTeam(),
                corredor.getGrupoSanguinio(),
                corredor.getConfirmado(),
                carreraResponseDTO,
                distanciaResponseDTO
        );
    }

}
