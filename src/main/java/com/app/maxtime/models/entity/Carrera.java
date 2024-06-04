package com.app.maxtime.models.entity;

import com.app.maxtime.dto.response.CarreraResponseDTO;
import com.app.maxtime.dto.response.DistanciaResponseDTO;
import com.app.maxtime.dto.response.OrganizadorResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
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
    @JoinColumn(name = "organizador_id")
    private Organizador organizador;

    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL)
    private List<Distancia> distancias;

    @Serial
    private static final long serialVersionUID = 1L;

    // Constructor to convert to CarreraResponseDTO
    public CarreraResponseDTO toResponseDTO() {
        OrganizadorResponseDTO organizadorResponse = new OrganizadorResponseDTO(
                organizador.getId(),
                organizador.getNombre(),
                organizador.getApellido(),
                organizador.getDni(),
                organizador.getEmail(),
                organizador.getTelefono()
        );

        List<DistanciaResponseDTO> distanciasResponse = distancias.stream()
                .map(distancia -> new DistanciaResponseDTO(
                        distancia.getId(),
                        distancia.getTipo(),
                        distancia.getValor(),
                        distancia.getLinkDePago(),
                        distancia.getCarrera().id,
                        distancia.getOrganizador().getId())) // Incluir el ID de la carrera
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
                organizadorResponse,
                distanciasResponse
        );
    }
}
