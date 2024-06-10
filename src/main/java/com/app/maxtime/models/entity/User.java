package com.app.maxtime.models.entity;


import com.app.maxtime.models.entity.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //aca va el mail del usuario
    @Email(message = "El formato del correo electrónico no es válido")
    private String username;
    private String apellido;
    private String dni;
    private String password;


    private String nombre;
    private String telefono;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Carrera> carreras;


    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, String apellido, String dni, String password, String nombre, String telefono, Role role) {
        this.username = username;
        this.apellido = apellido;
        this.dni = dni;
        this.password = password;
        this.nombre = nombre;
        this.telefono = telefono;
        this.role = role != null ? role : Role.CUSTOMER;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = role.getPermissions().stream()
                .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
                .collect(Collectors.toList());

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
