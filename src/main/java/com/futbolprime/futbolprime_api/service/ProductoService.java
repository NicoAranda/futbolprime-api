package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.producto.ActualizarProductoDTO;
import com.futbolprime.futbolprime_api.dto.producto.CrearProductoDTO;
import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;

import java.util.List;

public interface ProductoService {

    List<ProductoDTO> listarTodos();
    ProductoDTO buscarPorSku(String sku);
    List<ProductoDTO> listarPorTipo(String tipo);
    ProductoDTO crearProducto(CrearProductoDTO dto);
    void eliminarPorSku(String sku);
    ProductoDTO actualizarProducto(String sku, ActualizarProductoDTO dto);
}
