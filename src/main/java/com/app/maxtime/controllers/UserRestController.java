package com.app.maxtime.controllers;


import com.app.maxtime.dto.request.UserRequestDTO;
import com.app.maxtime.dto.response.UserResponseDTO;
import com.app.maxtime.models.entity.User;
import com.app.maxtime.models.entity.security.Role;
import com.app.maxtime.services.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/organizadores")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestController {

    @Autowired
    private IUserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PreAuthorize("permitAll")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO data){
        String password = data.password().toLowerCase();
        if (service.existsByUsername(data.username())) {
            // Manejar el caso donde ya existe un usuario con el mismo correo electrónico
            return new ResponseEntity<>("Ya existe un usuario con este correo electrónico", HttpStatus.CONFLICT);
        }

        // Verificar si ya existe un usuario con el mismo nombre de usuario
     //   if (service.existsByUsername(data.username())) {
            // Manejar el caso donde ya existe un usuario con el mismo nombre de usuario
         //   return new ResponseEntity<>("Ya existe ese nombre de usuario", HttpStatus.CONFLICT);
    //    }

        // Si el rol viene vacío, asignar CUSTOMER por defecto
        Role role = data.role() != null ? data.role() : Role.CUSTOMER;

        User newUser = new User(
                data.username(),
                data.apellido(),
                data.dni(),
                passwordEncoder.encode(password),
                data.nombre(),
                data.telefono(),
                role
        );

        service.createUser(newUser);

        UserResponseDTO userResponseDTO = new UserResponseDTO(newUser);

        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateOrganizador(
            @PathVariable Long id,
            @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO updatedUser = service.editeUser(id, userRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping
    @PreAuthorize("permitAll")
    public ResponseEntity<List<UserResponseDTO>> getAllOrganizadores(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            System.out.println("Token recibido: " + token); // Imprimir el token recibido

            String role = service.extractUserEmailFromToken(token);
            System.out.println("Rol extraído del token: " + role); // Imprimir el rol extraído del token

            if (!role.equals("ADMINISTRATOR")) {
                System.out.println("El usuario no tiene permisos de administrador."); // Imprimir mensaje de usuario sin permisos
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            List<UserResponseDTO> organizadores = service.findAll();
            return ResponseEntity.ok(organizadores);
        } catch (Exception e) {
            System.out.println("Error al procesar la solicitud: " + e.getMessage()); // Imprimir mensaje de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
