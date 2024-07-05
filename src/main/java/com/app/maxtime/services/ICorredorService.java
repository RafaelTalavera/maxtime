package com.app.maxtime.services;


import com.app.maxtime.models.entity.Corredor;


import java.util.List;
import java.util.Optional;

public interface ICorredorService {

    public List<Corredor> findAll();

    public Optional<Corredor> findById(Long id);

    public Corredor save(Corredor corredor);

    public void deleteById(Long id);

    public List<Corredor> findByCarreraId(Long carreraResponse);

    List<Corredor> findByDni(String dni);

    List<Corredor> getCorredoresByEmail(String email);

    String extractUserEmailFromToken(String token);

    Corredor updateConfirmado(Long corredorId);


}
