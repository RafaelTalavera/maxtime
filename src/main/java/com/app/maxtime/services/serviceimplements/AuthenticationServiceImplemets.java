package com.app.maxtime.services.serviceimplements;


import com.app.maxtime.dto.request.AuthenticationRequestDTO;
import com.app.maxtime.dto.response.AuthenticationResponseDTO;
import com.app.maxtime.models.dao.IUserDAO;

import com.app.maxtime.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImplemets {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserDAO userDao;

    @Autowired
    private JwtServiceImplements jwtService;

    public AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationRequest) {
        // Imprimir los datos recibidos del frontend
        System.out.println("Datos recibidos del frontend: " + authenticationRequest);

        // Crear el token de autenticación
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                (authenticationRequest.username(), authenticationRequest.password());

        // Imprimir el token de autenticación
        System.out.println("Token de autenticación: " + authenticationToken);

        // Intentar autenticar al usuario
        authenticationManager.authenticate(authenticationToken);

        // Obtener al usuario desde la base de datos
        User user = userDao.findByUsername(authenticationRequest.username()).get();

        // Imprimir los datos del usuario
        System.out.println("Datos del usuario autenticado: " + user);

        // Generar el token JWT
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        // Imprimir el token JWT generado
        System.out.println("Token JWT generado: " + jwt);

        // Crear y retornar la respuesta DTO
        return new AuthenticationResponseDTO(jwt);
    }

    private Map<String, Object> generateExtraClaims(User user){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("mail", user.getNombre());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("permissions", user.getAuthorities());
        return extraClaims;
    }



}
