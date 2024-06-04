package com.app.maxtime.models.dao;

import com.app.maxtime.models.entity.Distancia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IDistanciaDao extends CrudRepository<Distancia, Long> {

    @Query("SELECT d FROM Distancia d WHERE d.organizador.id = ?1 AND d.carrera.id = ?2")
    List<Distancia> findByOrganizadorIdAndCarreraId(Long organizadorId, Long carreraId);
}
