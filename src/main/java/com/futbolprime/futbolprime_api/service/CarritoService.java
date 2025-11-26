package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.carrito.*;

import java.util.List;

public interface CarritoService {

    CarritoDTO obtenerCarritoActivoPorUsuario(Long usuarioId);

    CarritoItemDTO agregarProductoACarrito(CrearCarritoItemDTO dto);

    CarritoItemDTO actualizarItem(Long itemId, ActualizarCarritoItemDTO dto);

    void eliminarItem(Long carritoId, Long productoId);

    void vaciarCarrito(Long carritoId);

    List<CarritoItemDTO> listarItems(Long carritoId);
}
