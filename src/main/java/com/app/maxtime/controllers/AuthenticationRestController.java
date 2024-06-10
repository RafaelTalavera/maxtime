package com.app.maxtime.controllers;


import com.app.maxtime.dto.request.AuthenticationRequestDTO;
import com.app.maxtime.dto.response.AuthenticationResponseDTO;
import com.app.maxtime.services.serviceimplements.AuthenticationServiceImplemets;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {


    @Autowired
    private AuthenticationServiceImplemets authenticationService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> login(
            @RequestBody @Valid AuthenticationRequestDTO authRequest){
        System.out.println("Método login() fue invocado con la solicitud: " + authRequest);
        AuthenticationResponseDTO jwtDto = authenticationService.login(authRequest);
        return ResponseEntity.ok(jwtDto);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("permitAll")
    @GetMapping("/public-access")
    public String publicAccessEndpoint(){
        return "este endpoint es público";
    }



}
