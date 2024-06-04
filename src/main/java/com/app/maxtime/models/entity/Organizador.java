package com.app.maxtime.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organizadores")
public class Organizador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono;

    @OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL)
    private List<Carrera> carreras;

    @Serial
    private static final long serialVersionUID = 1L;

}
