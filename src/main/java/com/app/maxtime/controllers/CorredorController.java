package com.app.maxtime.controllers;

import com.app.maxtime.dto.request.CorredorRequestDTO;
import com.app.maxtime.dto.response.CarreraResponseDTO;
import com.app.maxtime.dto.response.CorredorResponseDTO;
import com.app.maxtime.dto.response.DistanciaResponseDTO;
import com.app.maxtime.mapper.CorredorMapper;
import com.app.maxtime.models.dao.ICorredorDAO;
import com.app.maxtime.models.entity.Carrera;
import com.app.maxtime.models.entity.Corredor;
import com.app.maxtime.models.entity.Distancia;
import com.app.maxtime.services.ICarreraService;
import com.app.maxtime.services.ICorredorService;
import com.app.maxtime.services.IDistanciaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/corredores")
@CrossOrigin(origins = "http://localhost:4200")
public class CorredorController {

    @Autowired
    private ICorredorService corredorService;

    @Autowired
    private ICorredorDAO iCorredorDAO;

    @Autowired
    private IDistanciaService distanciaService;

    @Autowired
    private ICarreraService carreraService;


    @GetMapping("/usuario")
    public ResponseEntity<List<CorredorResponseDTO>>
    getCorredoresByUserEmail(HttpServletRequest request) {

        try {
        String token = request.getHeader("Authorization");
        String email = corredorService.extractUserEmailFromToken(token);
        List<Corredor> corredores = corredorService.getCorredoresByEmail(email);
        List<CorredorResponseDTO> corredoresResponse = convertToCorredorResponseDTO(corredores);
        return ResponseEntity.ok(corredoresResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/carrera/{carreraId}")
    public ResponseEntity<List<CorredorResponseDTO>> getCorredoresByCarreraId(@PathVariable Long carreraId) {
        List<Corredor> corredores = corredorService.findByCarreraId(carreraId);
        List<CorredorResponseDTO> corredoresResponse = convertToCorredorResponseDTO(corredores);
        return ResponseEntity.ok(corredoresResponse);
    }


    @GetMapping("/carrera/inscripto/{dni}")
    public ResponseEntity<List<CorredorResponseDTO>> getCorredoresByCarreraId(@PathVariable String dni) {
        List<Corredor> corredores = corredorService.findByDni(dni);
        List<CorredorResponseDTO> corredoresResponse = convertToCorredorResponseDTO(corredores);
        return ResponseEntity.ok(corredoresResponse);
    }

    @PostMapping
    public ResponseEntity<?> createCorredor(@RequestBody CorredorRequestDTO corredorRequestDTO) {
        try {
            System.out.println("Iniciando el proceso de creación de corredor...");

            Corredor corredor = new Corredor();
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

            Boolean confirmado = corredorRequestDTO.confirmado();
            corredor.setConfirmado(confirmado != null ? confirmado : false);

            System.out.println("Buscando la carrera con ID: " + corredorRequestDTO.carreraId());
            Carrera carrera = carreraService.findById(corredorRequestDTO.carreraId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la carrera con el ID: " + corredorRequestDTO.carreraId()));

            System.out.println("Buscando la distancia con ID: " + corredorRequestDTO.distanciaId());
            Distancia distancia = distanciaService.findById(corredorRequestDTO.distanciaId())
                    .orElseThrow(() -> new RuntimeException("No se encontró la distancia con el ID: " + corredorRequestDTO.distanciaId()));

            corredor.setCarrera(carrera);
            corredor.setDistancia(distancia);

            System.out.println("Verificando si el corredor con DNI " + corredor.getDni() + " ya está registrado en la carrera con ID " + carrera.getId());
            Corredor savedCorredor = corredorService.save(corredor);

            System.out.println("Corredor creado exitosamente");
            CorredorResponseDTO corredorResponseDTO = CorredorMapper.toDTO(savedCorredor);
            return ResponseEntity.ok(corredorResponseDTO);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: El corredor con ese DNI ya está registrado en esta carrera");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El corredor con ese DNI ya está registrado en esta carrera");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    @PutMapping("/{corredorId}/confirmado")
    public ResponseEntity<CorredorResponseDTO> updateConfirmado(@PathVariable Long corredorId) {
        Corredor corredor = corredorService.updateConfirmado(corredorId);
        CorredorResponseDTO corredorResponseDTO = CorredorMapper.toDTO(corredor);
        return ResponseEntity.ok(corredorResponseDTO);
    }

    private List<CorredorResponseDTO> convertToCorredorResponseDTO(List<Corredor> corredores) {
        return corredores.stream().map(this::mapToCorredorResponseDTO).collect(Collectors.toList());
    }
    private CorredorResponseDTO mapToCorredorResponseDTO(Corredor corredor) {
        Carrera carrera = corredor.getCarrera();
        Distancia distancia = corredor.getDistancia();

        CarreraResponseDTO carreraResponseDTO = new CarreraResponseDTO(carrera.getId(), carrera.getNombre(),
                carrera.getFecha(), carrera.getFechaDeCierreDeInscripcion(), carrera.getLocalidad(), carrera.getProvincia(),
                carrera.getPais(), carrera.getImagen(), carrera.getDetalles(), carrera.getContacto(), carrera.getHorario(),
                carrera.getEstado(), null, null);

        DistanciaResponseDTO distanciaResponseDTO = new DistanciaResponseDTO(distancia.getId(), distancia.getTipo(),
                distancia.getValor(), distancia.getLinkDePago(), null, null);

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
                corredor.isConfirmado(),
                carreraResponseDTO,
                distanciaResponseDTO
        );
    }

}
