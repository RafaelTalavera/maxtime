package com.app.maxtime.controllers;

import com.app.maxtime.dto.request.CarreraRequestDTO;
import com.app.maxtime.dto.response.CarreraResponseDTO;
import com.app.maxtime.services.ICarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carreras")
@CrossOrigin(origins = "http://localhost:4200")
public class CarreraController {

    @Autowired
    private ICarreraService carreraService;


    @PostMapping
    public ResponseEntity<CarreraResponseDTO> createCarrera(@RequestBody CarreraRequestDTO carreraRequestDTO) {
        CarreraResponseDTO carreraResponseDTO = carreraService.createCarrera(carreraRequestDTO);
        return ResponseEntity.ok(carreraResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CarreraResponseDTO>> getAllCarrera(){
        List<CarreraResponseDTO> carreras = carreraService.findAll();
        return ResponseEntity.ok(carreras);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<CarreraResponseDTO>> getActiveCarreras() {
        List<CarreraResponseDTO> carreras = carreraService.findActiveCarreras();
        return ResponseEntity.ok(carreras);
    }

    @GetMapping("/organizador/{organizadorId}")
    public ResponseEntity<List<CarreraResponseDTO>> getCarrerasByOrganizadorId(@PathVariable Long organizadorId) {
        List<CarreraResponseDTO> carreras = carreraService.findByOrganizadorId(organizadorId);
        return ResponseEntity.ok(carreras);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> updateCarrera(
            @PathVariable Long id,
            @RequestBody CarreraRequestDTO carreraRequestDTO) {
        CarreraResponseDTO updatedCarrera = carreraService.update(id,carreraRequestDTO);
        return ResponseEntity.ok(updatedCarrera);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrera(@PathVariable Long id) {
        carreraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}