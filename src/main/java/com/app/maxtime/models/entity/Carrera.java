package com.app.maxtime.models.entity;

import com.app.maxtime.dto.response.CarreraResponseDTO;
import com.app.maxtime.dto.response.DistanciaResponseDTO;
import com.app.maxtime.dto.response.UserResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carreras")
@Entity
public class Carrera implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Date fecha;

    private Date fechaDeCierreDeInscripcion;

    private String localidad;
    private String provincia;
    private String pais;
    private String imagen;
    private String detalles;
    private String contacto;
    private String horario;
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL)
    private List<Distancia> distancias;

    @Serial
    private static final long serialVersionUID = 1L;

    // Constructor to convert to CarreraResponseDTO
    public CarreraResponseDTO toResponseDTO() {
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getApellido(),
                user.getDni(),
                user.getPassword(),
                user.getNombre(),
                user.getTelefono(),
                user.getRole()

        );

        List<DistanciaResponseDTO> distanciasResponse = distancias.stream()
                .map(distancia -> new DistanciaResponseDTO(
                        distancia.getId(),
                        distancia.getTipo(),
                        distancia.getValor(),
                        distancia.getLinkDePago(),
                        distancia.getCarrera().id,
                        distancia.getUser().getId())) // Incluir el ID de la carrera
                .collect(Collectors.toList());

        return new CarreraResponseDTO(
                id,
                nombre,
                fecha,
                fechaDeCierreDeInscripcion,
                localidad,
                provincia,
                pais,
                imagen,
                detalles,
                contacto,
                horario,
                estado,
                userResponseDTO,
                distanciasResponse
        );
    }
}
