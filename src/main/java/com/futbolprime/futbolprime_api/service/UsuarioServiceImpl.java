package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.usuario.*;
import com.futbolprime.futbolprime_api.model.Usuario;
import com.futbolprime.futbolprime_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDTO crearUsuario(CrearUsuarioDTO dto) {

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email es obligatorio");
        }
        if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo no es válido");
        }
        if (usuarioRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }
        if (dto.getPassword().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña debe tener al menos 6 caracteres");
        }
        if (dto.getRol() == null || dto.getRol().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol es obligatorio");
        }

        // Normalizamos el rol
        String rolNormalizado = dto.getRol().trim();

        // Acepta ADMIN, CLIENTE y VENDEDOR
        if (!rolNormalizado.equalsIgnoreCase("ADMIN")
                && !rolNormalizado.equalsIgnoreCase("CLIENTE")
                && !rolNormalizado.equalsIgnoreCase("VENDEDOR")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El rol debe ser ADMIN, CLIENTE o VENDEDOR"
            );
        }

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .rol(rolNormalizado.toUpperCase())
                .habilitado(Boolean.TRUE.equals(dto.getHabilitado()))
                .build();

        Usuario guardado = usuarioRepository.save(usuario);
        return toDTO(guardado);
    }

    @Override
    public UsuarioDTO obtenerUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"
                ));

        return toDTO(usuario);
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public UsuarioDTO actualizarUsuario(Long id, ActualizarUsuarioDTO dto) {

        // 1) Buscar usuario o lanzar 404
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"
                ));

        // 2) Actualizar nombre (opcional)
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            usuario.setNombre(dto.getNombre());
        }

        // 3) Actualizar email (opcional)
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            String nuevoEmail = dto.getEmail().trim();

            // Validar formato
            if (!nuevoEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo no es válido");
            }

            // Validar que no esté usado por otro usuario
            if (!nuevoEmail.equalsIgnoreCase(usuario.getEmail())
                    && usuarioRepository.existsByEmailIgnoreCase(nuevoEmail)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
            }

            usuario.setEmail(nuevoEmail);
        }

        // 4) Actualizar contraseña (opcional)
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            if (dto.getPassword().length() < 6) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "La contraseña debe tener al menos 6 caracteres");
            }
            usuario.setPassword(dto.getPassword());
        }

        // 5) Actualizar rol (opcional)
        if (dto.getRol() != null && !dto.getRol().isBlank()) {

            String rolNormalizado = dto.getRol().trim();

            if (!rolNormalizado.equalsIgnoreCase("ADMIN")
                    && !rolNormalizado.equalsIgnoreCase("CLIENTE")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El rol debe ser ADMIN o CLIENTE");
            }

            usuario.setRol(rolNormalizado.toUpperCase());
        }

        // 6) Actualizar habilitado (opcional)
        if (dto.getHabilitado() != null) {
            usuario.setHabilitado(dto.getHabilitado());
        }

        // 7) Guardar y devolver DTO
        Usuario actualizado = usuarioRepository.save(usuario);
        return toDTO(actualizado);
    }


    @Override
    public void eliminarUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"
                ));

        try {
            usuarioRepository.delete(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede eliminar el usuario porque tiene datos asociados"
            );
        }
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email es obligatorio");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }

        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));

        if (!usuario.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        }

        return LoginResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .build();
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .build();
    }
}
