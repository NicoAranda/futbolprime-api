package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.usuario.*;
import com.futbolprime.futbolprime_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(
            summary = "Registrar un nuevo usuario",
            description = "Crea un nuevo usuario en el sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario creado exitosamente",
                            content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos enviados"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<UsuarioDTO> registrar(@RequestBody CrearUsuarioDTO dto) {
        UsuarioDTO creado = usuarioService.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Login (devuelve datos básicos; más adelante puedes añadir JWT)
    @Operation(
            summary = "Login de usuario",
            description = "Permite iniciar sesión con email y contraseña",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login exitoso",
                            content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciales incorrectas"
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO resp = usuarioService.login(dto);
        return ResponseEntity.ok(resp);
    }

    // Listar todos los usuarios (uso administrativo)
    @Operation(
            summary = "Listar todos los usuarios",
            description = "Devuelve una lista de todos los usuarios registrados",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista obtenida exitosamente",
                            content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<UsuarioDTO> lista = usuarioService.listarUsuarios();
        return ResponseEntity.ok(lista);
    }

    // Obtener usuario por id
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Obtiene la información de un usuario según su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario encontrado",
                            content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuario no encontrado"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        UsuarioDTO dto = usuarioService.obtenerUsuario(id);
        return ResponseEntity.ok(dto);
    }

    // Actualizar usuario (envía solo los campos a cambiar)
    @Operation(
            summary = "Actualizar usuario",
            description = "Modifica la información de un usuario existente",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario actualizado exitosamente",
                            content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuario no encontrado"
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarUsuarioDTO dto
    ) {
        UsuarioDTO actualizado = usuarioService.actualizarUsuario(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar usuario
    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario del sistema por su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuario eliminado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuario no encontrado"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
