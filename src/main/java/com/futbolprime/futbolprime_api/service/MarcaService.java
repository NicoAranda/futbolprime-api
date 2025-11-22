package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.marca.ActualizarMarcaDTO;
import com.futbolprime.futbolprime_api.dto.marca.CrearMarcaDTO;
import com.futbolprime.futbolprime_api.dto.marca.MarcaDTO;

import java.util.List;

public interface MarcaService {
    List<MarcaDTO> listarTodas();

    MarcaDTO buscarPorId(Long id);

    MarcaDTO buscarPorSlug(String slug);

    MarcaDTO crearMarca(CrearMarcaDTO dto);

    void eliminarPorId(Long id);

    MarcaDTO actualizarMarca(Long id, ActualizarMarcaDTO dto);
}
