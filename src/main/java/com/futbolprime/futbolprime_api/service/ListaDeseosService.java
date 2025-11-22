package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.listaDeseos.ActualizarListaDeseosDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.CrearListaDeseosDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.ListaDeseosDTO;

import java.util.List;

public interface ListaDeseosService {

    List<ListaDeseosDTO> listarPorUsuario(String usuarioId);

    ListaDeseosDTO agregar(CrearListaDeseosDTO dto);

    void eliminar(String usuarioId, Long productoId);

    // 🔹 NUEVO
    ListaDeseosDTO actualizar(Long id, ActualizarListaDeseosDTO dto);
}
