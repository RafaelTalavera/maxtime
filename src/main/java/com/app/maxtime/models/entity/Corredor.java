package com.app.maxtime.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "corredores")
public class Corredor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String fechaNacimiento;
    private String genero;
    private String nacionalidad;
    private String provincia;
    private String localidad;
    private String talle;
    private String telefono;
    private String email;
    private String team;
    private boolean confirmado;
    private String grupoSanguinio;

    @ManyToOne
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @ManyToOne
    @JoinColumn(name = "distancia_id")
    private Distancia distancia;

    @Serial
    private static final long serialVersionUID = 1L;

}
