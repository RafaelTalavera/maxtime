package com.app.maxtime.mapper;

import com.app.maxtime.dto.response.CarreraResponseDTO;
import com.app.maxtime.dto.response.CorredorResponseDTO;
import com.app.maxtime.dto.response.DistanciaResponseDTO;
import com.app.maxtime.dto.response.OrganizadorResponseDTO;
import com.app.maxtime.models.entity.Corredor;
import com.app.maxtime.models.entity.Carrera;
import com.app.maxtime.models.entity.Distancia;
import com.app.maxtime.models.entity.Organizador;

public class CorredorMapper {

    public static CorredorResponseDTO toDTO(Corredor corredor) {
        return new CorredorResponseDTO(
                corredor.getId(),
                corredor.getNombre(),
                corredor.getApellido(),
                corredor.getDni(),
                corredor.getFechaNacimiento(),
                corredor.getGenero(),
                corredor.getNacionalidad(),
                corredor.getProvincia(),
                corredor.getLocalidad(),
                corredor.getTalle(),
                corredor.getTelefono(),
                corredor.getEmail(),
                corredor.getTeam(),
                corredor.getGrupoSanguinio(),
                corredor.getConfirmado(),
                carreraToDTO(corredor.getCarrera()),
                distanciaToDTO(corredor.getDistancia())
        );
    }

    // Método para convertir la entidad Carrera a su DTO correspondiente
    private static CarreraResponseDTO carreraToDTO(Carrera carrera) {
        if (carrera == null) {
            return null;
        }
        OrganizadorResponseDTO organizadorDTO = organizadorToDTO(carrera.getOrganizador());
        return new CarreraResponseDTO(
                carrera.getId(),
                carrera.getNombre(),
                carrera.getFecha(),
                carrera.getFechaDeCierreDeInscripcion(),
                carrera.getLocalidad(),
                carrera.getProvincia(),
                carrera.getPais(),
                carrera.getImagen(),
                carrera.getDetalles(),
                carrera.getContacto(),
                carrera.getHorario(),
                carrera.getEstado(),
                organizadorDTO,
                null // Ejemplo de otro atributo adicional si es necesario
        );
    }

    // Método para convertir la entidad Distancia a su DTO correspondiente
    private static DistanciaResponseDTO distanciaToDTO(Distancia distancia) {
        if (distancia == null) {
            return null;
        }
        return new DistanciaResponseDTO(
                distancia.getId(),
                distancia.getTipo(),
                distancia.getValor(),
                distancia.getLinkDePago(),
                distancia.getCarrera().getId(),
                distancia.getOrganizador().getId()
        );
    }

    // Método para convertir la entidad Organizador a su DTO correspondiente
    private static OrganizadorResponseDTO organizadorToDTO(Organizador organizador) {
        if (organizador == null) {
            return null;
        }
        // Lógica para mapear la entidad Organizador a su DTO OrganizadorResponseDTO
        return new OrganizadorResponseDTO(
                organizador.getId(),
                organizador.getNombre(),
                organizador.getDni(),
                organizador.getEmail(),
                organizador.getTelefono(),
                organizador.getTelefono()

                // Incluir más atributos si es necesario
        );
    }
}
