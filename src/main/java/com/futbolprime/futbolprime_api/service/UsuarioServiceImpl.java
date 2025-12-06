package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.usuario.*;
import com.futbolprime.futbolprime_api.model.Usuario;
import com.futbolprime.futbolprime_api.repository.UsuarioRepository;
import com.futbolprime.futbolprime_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // ---------------------------------------------------------
    //  CREAR USUARIO
    // ---------------------------------------------------------
    @Override
    public UsuarioDTO crearUsuario(CrearUsuarioDTO dto) {

        // Validar email
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email es obligatorio");
        }
        if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo no es válido");
        }
        if (usuarioRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
        }

        // Validar contraseña
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }
        if (dto.getPassword().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña debe tener al menos 6 caracteres");
        }

        // Validar rol
        if (dto.getRol() == null || dto.getRol().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol es obligatorio");
        }

        String rolNormalizado = dto.getRol().trim().toUpperCase();

        if (!rolNormalizado.equals("ADMIN") &&
                !rolNormalizado.equals("CLIENTE") &&
                !rolNormalizado.equals("VENDEDOR")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El rol debe ser ADMIN, CLIENTE o VENDEDOR"
            );
        }

        // Crear usuario
        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword())) // 👈 encriptada
                .rol(rolNormalizado.toUpperCase())
                .habilitado(Boolean.TRUE.equals(dto.getHabilitado()))
                .build();

        Usuario guardado = usuarioRepository.save(usuario);
        return toDTO(guardado);
    }

    // ---------------------------------------------------------
    //  OBTENER USUARIO
    // ---------------------------------------------------------
    @Override
    public UsuarioDTO obtenerUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"
                ));

        return toDTO(usuario);
    }

    // ---------------------------------------------------------
    //  LISTAR USUARIOS
    // ---------------------------------------------------------
    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // ---------------------------------------------------------
    //  ACTUALIZAR USUARIO
    // ---------------------------------------------------------
    @Override
    public UsuarioDTO actualizarUsuario(Long id, ActualizarUsuarioDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"
                ));

        // Actualizar nombre
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            usuario.setNombre(dto.getNombre());
        }

        // Actualizar email
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            String nuevoEmail = dto.getEmail().trim();

            if (!nuevoEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo no es válido");
            }

            if (!nuevoEmail.equalsIgnoreCase(usuario.getEmail())
                    && usuarioRepository.existsByEmailIgnoreCase(nuevoEmail)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
            }

            usuario.setEmail(nuevoEmail);
        }

        // Actualizar contraseña
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {

            if (dto.getPassword().length() < 6) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "La contraseña debe tener al menos 6 caracteres");
            }

            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // Actualizar rol
        if (dto.getRol() != null && !dto.getRol().isBlank()) {

            String rolNormalizado = dto.getRol().trim().toUpperCase();

            if (!rolNormalizado.equals("ADMIN") &&
                    !rolNormalizado.equals("CLIENTE") &&
                    !rolNormalizado.equals("VENDEDOR")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El rol debe ser ADMIN, CLIENTE o VENDEDOR");
            }

            usuario.setRol(rolNormalizado);
        }

        // Actualizar habilitado
        if (dto.getHabilitado() != null) {
            usuario.setHabilitado(dto.getHabilitado());
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return toDTO(actualizado);
    }

    // ---------------------------------------------------------
    //  ELIMINAR USUARIO
    // ---------------------------------------------------------
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

    // ---------------------------------------------------------
    //  LOGIN
    // ---------------------------------------------------------
    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email es obligatorio");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }

        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"
                ));

        // Comparar contraseña encriptada
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        }

        // Generar token
        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());

        return LoginResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .token(token)
                .build();
    }

    // ---------------------------------------------------------
    //  CONVERSIÓN A DTO
    // ---------------------------------------------------------
    private UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .build();
    }
}
