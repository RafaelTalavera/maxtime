package com.app.maxtime.models.dao;

import com.app.maxtime.models.entity.Corredor;
import com.app.maxtime.models.entity.Distancia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICorredorDAO extends CrudRepository<Corredor, Long> {

    List<Corredor> findByCarreraId(Long carreraId);

    List<Corredor> findByDni(String dni);

    List<Corredor> findByDistanciaIn(List<Distancia> distancias);

    Optional<Corredor> findByDniAndCarreraId(String dni, Long carreraId);
}
