package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.usuario.*;
import com.futbolprime.futbolprime_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Registro de usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> registrar(@RequestBody CrearUsuarioDTO dto) {
        UsuarioDTO creado = usuarioService.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Login (devuelve datos básicos; más adelante puedes añadir JWT)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO resp = usuarioService.login(dto);
        return ResponseEntity.ok(resp);
    }

    // Listar todos los usuarios (uso administrativo)
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<UsuarioDTO> lista = usuarioService.listarUsuarios();
        return ResponseEntity.ok(lista);
    }

    // Obtener usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        UsuarioDTO dto = usuarioService.obtenerUsuario(id);
        return ResponseEntity.ok(dto);
    }

    // Actualizar usuario (envía solo los campos a cambiar)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarUsuarioDTO dto
    ) {
        UsuarioDTO actualizado = usuarioService.actualizarUsuario(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
