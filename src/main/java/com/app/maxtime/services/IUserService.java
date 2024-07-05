package com.app.maxtime.services;


import com.app.maxtime.dto.request.UserRequestDTO;
import com.app.maxtime.dto.response.UserResponseDTO;
import com.app.maxtime.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public List<UserResponseDTO> findAll();

    Optional<User> findById(Long id);

    User createUser(User user);

    public void delete(User user);

    UserResponseDTO editeUser(Long id, UserRequestDTO editedUser);

    boolean existsByUsername(String username);

    public String extractUserEmailFromToken(String token);

    public Long extractUserIdFromToken(String token);

}
