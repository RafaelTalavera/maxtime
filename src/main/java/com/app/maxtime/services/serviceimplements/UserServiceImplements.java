package com.app.maxtime.services.serviceimplements;




import com.app.maxtime.dto.request.UserRequestDTO;
import com.app.maxtime.dto.response.UserResponseDTO;
import com.app.maxtime.exeption.RegistroNoEncontradoException;
import com.app.maxtime.models.dao.IUserDAO;
import com.app.maxtime.models.entity.User;
import com.app.maxtime.services.IUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class UserServiceImplements implements IUserService {

    @Autowired
    private IUserDAO userDao;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;


    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        // Obtiene todos los usuarios de la base de datos
        Iterable<User> users = userDao.findAll();

        // Convierte cada usuario en un DTO de respuesta de usuario y los recopila en una lista
        List<UserResponseDTO> userDTOs = StreamSupport.stream(users.spliterator(), false)
                .map(UserResponseDTO::new) // Utiliza el constructor de UserResponseDTO que toma un User como argumento
                .collect(Collectors.toList());

        return userDTOs;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return userDao.save(user);
    }


    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public UserResponseDTO editeUser(Long id, UserRequestDTO editedUser) {
        User existUser = userDao.findById(id)
                .orElseThrow(() -> new RegistroNoEncontradoException("No se encontró ningún registro con el ID: " + id));

        // Actualiza las propiedades del usuario existente con los valores de editedUser
        existUser.setUsername(editedUser.username());
        existUser.setApellido(editedUser.apellido());
        existUser.setPassword(editedUser.password());
        existUser.setNombre(editedUser.nombre());
        existUser.setDni(editedUser.dni());
        existUser.setTelefono(editedUser.telefono());
        existUser.setRole(editedUser.role());

        // Guarda los cambios en la base de datos
        User updatedUser = userDao.save(existUser);

        // Crea un objeto UserResponseDTO a partir del usuario actualizado y devuélvelo
        return new UserResponseDTO(updatedUser);
    }


    @Override
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    @Transactional
    public String extractUserEmailFromToken(String token) {
        try {
            // Remover la palabra "Bearer " del inicio del token
            String jwtToken = token.replace("Bearer ", "");
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();
            String role = claims.get("role", String.class);
            System.out.println("Role extraído del token: " + role); // Imprimir el rol extraído del token
            return role;
        } catch (Exception e) {
            throw new RuntimeException("Error al extraer el correo electrónico del token", e);
        }
    }


}
