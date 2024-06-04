package com.app.maxtime.services.serviceimplements;


import com.app.maxtime.models.dao.ICorredorDAO;
import com.app.maxtime.models.entity.Corredor;
import com.app.maxtime.services.ICorredorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorredorServiceImplemts implements ICorredorService {

    @Autowired
    ICorredorDAO corredorDAO;

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
}
