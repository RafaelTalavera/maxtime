package com.app.maxtime.controllers;

import com.app.maxtime.dto.request.OrganizadorRequestDTO;
import com.app.maxtime.dto.response.OrganizadorResponseDTO;
import com.app.maxtime.services.IOrganizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizadores")
@CrossOrigin(origins = "http://localhost:4200")
public class OrganizadorController {

    @Autowired
    private IOrganizadorService organizadorService;

    @PostMapping
    public ResponseEntity<OrganizadorResponseDTO> createOrganizador(@RequestBody OrganizadorRequestDTO organizadorRequestDTO) {
        OrganizadorResponseDTO organizadorResponseDTO = organizadorService.save(organizadorRequestDTO);
        return ResponseEntity.ok(organizadorResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrganizadorResponseDTO>> getAllOrganizadores() {
        List<OrganizadorResponseDTO> organizadores = organizadorService.findAll();
        return ResponseEntity.ok(organizadores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizadorResponseDTO> updateOrganizador(
            @PathVariable Long id,
            @RequestBody OrganizadorRequestDTO organizadorRequestDTO) {
        OrganizadorResponseDTO updatedOrganizador = organizadorService.update(id, organizadorRequestDTO);
        return ResponseEntity.ok(updatedOrganizador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizador(@PathVariable Long id) {
        organizadorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}


