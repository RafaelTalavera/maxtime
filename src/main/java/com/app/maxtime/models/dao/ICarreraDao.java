package com.app.maxtime.models.dao;

import com.app.maxtime.models.entity.Carrera;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICarreraDao extends CrudRepository<Carrera, Long> {

    List<Carrera> findByOrganizadorId(Long organizadorId);

    List<Carrera> findByEstadoTrue();
}
