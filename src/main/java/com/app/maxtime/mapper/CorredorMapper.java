package com.app.maxtime.mapper;

import com.app.maxtime.dto.response.CarreraResponseDTO;
import com.app.maxtime.dto.response.CorredorResponseDTO;
import com.app.maxtime.dto.response.DistanciaResponseDTO;
import com.app.maxtime.dto.response.UserResponseDTO;
import com.app.maxtime.models.entity.Corredor;
import com.app.maxtime.models.entity.Carrera;
import com.app.maxtime.models.entity.Distancia;
import com.app.maxtime.models.entity.User;

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
                corredor.isConfirmado(),
                carreraToDTO(corredor.getCarrera()),
                distanciaToDTO(corredor.getDistancia())
        );
    }

    // Método para convertir la entidad Carrera a su DTO correspondiente
    private static CarreraResponseDTO carreraToDTO(Carrera carrera) {
        if (carrera == null) {
            return null;
        }
        UserResponseDTO organizadorDTO = userToDTO(carrera.getUser());
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
                distancia.getUser().getId()
        );
    }

    // Método para convertir la entidad Organizador a su DTO correspondiente
    private static UserResponseDTO userToDTO(User user) {
        if (user == null) {
            return null;
        }
        // Lógica para mapear la entidad Organizador a su DTO OrganizadorResponseDTO
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getDni(),
                user.getNombre(),
                user.getTelefono(),
                user.getTelefono(),
                user.getPassword(),
                user.getRole()

                // Incluir más atributos si es necesario
        );
    }
}
