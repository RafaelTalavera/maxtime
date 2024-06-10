package com.app.maxtime.dto.response;

import com.app.maxtime.models.entity.User;
import com.app.maxtime.models.entity.security.Role;

public record UserResponseDTO(
        Long id,
        String username,
        String apellido,
        String dni,
        String password,
        String nombre,
        String telefono,
        Role role
) {
    public UserResponseDTO(User user) {
        this(user.getId(),
                user.getUsername(),
                user.getApellido(),
                user.getDni(),
                user.getPassword(),
                user.getNombre(),
                user.getTelefono(),
                user.getRole());
    }
}
