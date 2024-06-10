package com.app.maxtime.services.serviceimplements;


import com.app.maxtime.models.dao.ICorredorDAO;
import com.app.maxtime.models.dao.IDistanciaDao;
import com.app.maxtime.models.entity.Corredor;
import com.app.maxtime.models.entity.Distancia;
import com.app.maxtime.services.ICorredorService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CorredorServiceImplemts implements ICorredorService {

    @Autowired
    ICorredorDAO corredorDAO;

    @Autowired
    IDistanciaDao distanciaDAO;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    @Override
    public List<Corredor> findAll() {
        return (List<Corredor>) corredorDAO.findAll();
    }

    @Override
    public Optional<Corredor> findById(Long id) {
        return corredorDAO.findById(id);
    }

    @Override
    public Corredor save(Corredor corredor) {
        return corredorDAO.save(corredor);
    }

    @Override
    public void deleteById(Long id) {
                Corredor existCorredor = corredorDAO.findById(id).orElseThrow(()
                -> new RuntimeException("No se encontró ningún registro con el ID: " + id));
        corredorDAO.deleteById(id);

    }

    @Override
    public List<Corredor> findByCarreraId(Long carreraId) {
        return corredorDAO.findByCarreraId(carreraId);
    }

    @Override
    public List<Corredor> findByDni(String dni) {
        return corredorDAO.findByDni(dni);
    }


    @Override
    public List<Corredor> getCorredoresByEmail(String email) {
        // Obtener las distancias asociadas al usuario con el correo electrónico proporcionado
        List<Distancia> distancias = distanciaDAO.findByUserUsername(email);

        // Obtener los corredores asociados a las distancias encontradas
        return corredorDAO.findByDistanciaIn(distancias);
    }

    @Override
    @Transactional
    public String extractUserEmailFromToken(String token) {
        try {
            // Remover la palabra "Bearer " del inicio del token
            String jwtToken = token.replace("Bearer ", "");
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();
            return claims.get("sub", String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al extraer el correo electrónico del token", e);
        }
    }

    @Override
    @Transactional
    public Corredor updateConfirmado(Long corredorId) {
        Corredor corredor = corredorDAO.findById(corredorId)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún corredor con el ID: " + corredorId));

        // Cambiar el estado de 'confirmado'
        corredor.setConfirmado(!corredor.isConfirmado());

        return corredorDAO.save(corredor); // Guarda los cambios en la base de datos y devuelve el corredor actualizado
    }
}

