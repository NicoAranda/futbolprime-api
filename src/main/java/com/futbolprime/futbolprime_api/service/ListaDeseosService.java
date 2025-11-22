package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.listaDeseos.*;

import java.util.List;

public interface ListaDeseosService {

    ListaDeseosDTO obtenerPorUsuario(Long usuarioId);

    ListaDeseosItemDTO agregarProducto(CrearListaDeseosItemDTO dto);

    void eliminarProducto(Long usuarioId, Long productoId);

    List<ListaDeseosItemDTO> listarItems(Long usuarioId);
}
