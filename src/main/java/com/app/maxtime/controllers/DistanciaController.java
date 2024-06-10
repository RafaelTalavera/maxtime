package com.app.maxtime.controllers;

import com.app.maxtime.dto.request.DistanciaRequestDTO;
import com.app.maxtime.dto.response.DistanciaResponseDTO;
import com.app.maxtime.services.IDistanciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distancias")
@CrossOrigin(origins = "http://localhost:4200")
public class DistanciaController {

    @Autowired
    private IDistanciaService distanciaService;

    @GetMapping
    public ResponseEntity<List<DistanciaResponseDTO>> getAllDistancias() {
        List<DistanciaResponseDTO> distancias = distanciaService.getAllDistancias();
        return ResponseEntity.ok(distancias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistanciaResponseDTO> getDistanciaById(@PathVariable Long id) {
        DistanciaResponseDTO distancia = distanciaService.getDistanciaById(id);
        return ResponseEntity.ok(distancia);
    }

    @PostMapping
    public ResponseEntity<DistanciaResponseDTO> createDistancia(@RequestBody DistanciaRequestDTO distanciaRequestDTO) {
        DistanciaResponseDTO nuevaDistancia = distanciaService.createDistancia(distanciaRequestDTO);
        return ResponseEntity.ok(nuevaDistancia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistanciaResponseDTO> updateDistancia(@PathVariable Long id, @RequestBody DistanciaRequestDTO distanciaRequestDTO) {
        DistanciaResponseDTO distanciaActualizada = distanciaService.updateDistancia(id, distanciaRequestDTO);
        return ResponseEntity.ok(distanciaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistancia(@PathVariable Long id) {
        distanciaService.deleteDistancia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/organizador/{organizadorId}/carrera/{carreraId}")
    public ResponseEntity<List<DistanciaResponseDTO>> findByOrganizadorIdAndCarreraId(
            @PathVariable Long organizadorId, @PathVariable Long carreraId) {
        List<DistanciaResponseDTO> distancias = distanciaService.findByUserIdAndCarreraId(organizadorId, carreraId);
        return ResponseEntity.ok(distancias);
    }
}
