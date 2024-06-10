package com.app.maxtime.models.dao;


import com.app.maxtime.models.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserDAO extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
