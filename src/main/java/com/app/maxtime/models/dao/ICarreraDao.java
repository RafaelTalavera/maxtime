package com.app.maxtime.models.dao;

import com.app.maxtime.models.entity.Carrera;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICarreraDao extends CrudRepository<Carrera, Long> {

    List<Carrera> findByUserId(Long userId);
    List<Carrera> findByEstadoTrue();

}
