package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.categoria.ActualizarCategoriaDTO;
import com.futbolprime.futbolprime_api.dto.categoria.CrearCategoriaDTO;
import com.futbolprime.futbolprime_api.dto.categoria.CategoriaDTO;

import java.util.List;

public interface CategoriaService {

    List<CategoriaDTO> listarTodas();

    CategoriaDTO buscarPorId(Long id);

    CategoriaDTO buscarPorSlug(String slug);

    List<CategoriaDTO> listarPorPadre(Long padreId);

    CategoriaDTO crearCategoria(CrearCategoriaDTO dto);

    CategoriaDTO actualizarCategoria(Long id, ActualizarCategoriaDTO dto);

    void eliminarCategoria(Long id);
}
