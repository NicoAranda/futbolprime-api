package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.usuario.*;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO crearUsuario(CrearUsuarioDTO dto);

    UsuarioDTO obtenerUsuario(Long id);

    List<UsuarioDTO> listarUsuarios();

    UsuarioDTO actualizarUsuario(Long id,ActualizarUsuarioDTO dto);

    void eliminarUsuario(Long id);

    LoginResponseDTO login(LoginRequestDTO dto);
}
