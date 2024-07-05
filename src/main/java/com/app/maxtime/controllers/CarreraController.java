package com.app.maxtime.controllers;

import com.app.maxtime.dto.request.CarreraRequestDTO;
import com.app.maxtime.dto.response.CarreraResponseDTO;
import com.app.maxtime.services.ICarreraService;
import com.app.maxtime.services.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/carreras")
@CrossOrigin(origins = "http://localhost:4200")
public class CarreraController {

    @Autowired
    private ICarreraService carreraService;

    @Autowired
    private IUserService userService;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


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

    @PreAuthorize("permitAll")
    @GetMapping("/activas")
    public ResponseEntity<List<CarreraResponseDTO>> getActiveCarreras() {
        List<CarreraResponseDTO> carreras = carreraService.findActiveCarreras();
        return ResponseEntity.ok(carreras);
    }

    @GetMapping("/organizador")
    public ResponseEntity<List<CarreraResponseDTO>> getCarrerasByUserId(
                                                                        HttpServletRequest request) {

        try {
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            Long userId = userService.extractUserIdFromToken(token);

            List<CarreraResponseDTO> carreras = carreraService.findByUserId(userId);
            if (carreras.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(carreras);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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