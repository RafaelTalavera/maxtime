package com.app.maxtime.models.dao;

import com.app.maxtime.models.entity.Distancia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDistanciaDao extends CrudRepository<Distancia, Long> {

    @Query("SELECT d FROM Distancia d WHERE d.user.id = ?1 AND d.carrera.id = ?2")
    List<Distancia> findByUserIdAndCarreraId(Long userId, Long carreraId);

    @Query("SELECT d FROM Distancia d WHERE d.user.username = :username")
    List<Distancia> findByUserUsername(String username);

}
