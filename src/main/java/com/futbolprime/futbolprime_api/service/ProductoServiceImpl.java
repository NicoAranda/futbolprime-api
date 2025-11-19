package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.ActualizarProductoDTO;
import com.futbolprime.futbolprime_api.dto.CrearProductoDTO;
import com.futbolprime.futbolprime_api.dto.ProductoDTO;
import com.futbolprime.futbolprime_api.model.Producto;
import com.futbolprime.futbolprime_api.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ProductoDTO buscarPorSku(String sku) {
        Producto producto = productoRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException(("Producto no encontrado con SKU: "+sku)));
        return toDTO(producto);
    }

    @Override
    public List<ProductoDTO> listarPorTipo(String tipo) {
        return productoRepository.findByTipoIgnoreCase(tipo)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ProductoDTO crearProducto(CrearProductoDTO dto) {
        Producto producto = Producto.builder()
                .sku(dto.getSku())
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .oferta(dto.getOferta())
                .tipo(dto.getTipo())
                .talla(dto.getTalla())
                .color(dto.getColor())
                .stock(dto.getStock())
                .marca(dto.getMarca())
                .descripcion(dto.getDescripcion())
                .imagen(dto.getImagen())
                .build();
        Producto guardado = productoRepository.save(producto);
        return toDTO(guardado);
    }

    @Override
    public void eliminarPorSku(String sku) {
        var producto = productoRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con SKU: " + sku));

        productoRepository.delete(producto);
    }

    @Override
    public ProductoDTO actualizarProducto(String sku, ActualizarProductoDTO dto) {

        var producto = productoRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualizar solo los campos enviados
        if (dto.getNombre() != null) producto.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) producto.setDescripcion(dto.getDescripcion());
        if (dto.getPrecio() != null) producto.setPrecio(dto.getPrecio());
        if (dto.getOferta() != null) producto.setOferta(dto.getOferta());
        if (dto.getStock() != null) producto.setStock(dto.getStock());
        if (dto.getTipo() != null) producto.setTipo(dto.getTipo());
        if (dto.getColor() != null) producto.setColor(dto.getColor());
        if (dto.getTalla() != null) producto.setTalla(dto.getTalla());

        var actualizado = productoRepository.save(producto);

        return toDTO(actualizado);
    }

    private ProductoDTO toDTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setSku(p.getSku());
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());
        dto.setOferta(p.getOferta());
        dto.setTipo(p.getTipo());
        dto.setTalla(p.getTalla());
        dto.setStock(p.getStock());
        dto.setColor(p.getColor());
        dto.setMarca(p.getMarca());
        dto.setImagen(p.getImagen());
        return dto;
    }
}
