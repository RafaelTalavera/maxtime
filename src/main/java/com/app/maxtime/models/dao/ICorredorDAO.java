package com.app.maxtime.models.dao;

import com.app.maxtime.models.entity.Corredor;
import com.app.maxtime.models.entity.Distancia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICorredorDAO extends CrudRepository<Corredor, Long> {

    List<Corredor> findByCarreraId(Long carreraId);

    List<Corredor> findByDni(String dni);

    List<Corredor> findByDistanciaIn(List<Distancia> distancias);

}
