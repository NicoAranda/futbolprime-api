package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.carrito.*;

import java.util.List;

public interface CarritoService {

    List<CarritoDTO> listarTodos();
    List<CarritoDTO> listarPorUsuario(Long usuarioId);
    CarritoDTO buscarPorId(Long id);
    CarritoDTO crearCarrito(CrearCarritoDTO dto);
    CarritoDTO actualizarCarrito(Long id, ActualizarCarritoDTO dto);
    void eliminarCarrito(Long id);
}
