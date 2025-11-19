package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.ActualizarProductoDTO;
import com.futbolprime.futbolprime_api.dto.CrearProductoDTO;
import com.futbolprime.futbolprime_api.dto.ProductoDTO;
import com.futbolprime.futbolprime_api.model.Producto;

import java.util.List;

public interface ProductoService {

    List<ProductoDTO> listarTodos();
    ProductoDTO buscarPorSku(String sku);
    List<ProductoDTO> listarPorTipo(String tipo);
    ProductoDTO crearProducto(CrearProductoDTO dto);
    void eliminarPorSku(String sku);
    ProductoDTO actualizarProducto(String sku, ActualizarProductoDTO dto);
}
