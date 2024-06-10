package com.app.maxtime.dto.request;


import com.app.maxtime.models.entity.security.Role;

public record UserRequestDTO(
        String username,
        String apellido,
        String dni,
        String password,
        String nombre,
        String telefono,
        Role role

) {


}
