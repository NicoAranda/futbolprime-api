package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.producto.ActualizarProductoDTO;
import com.futbolprime.futbolprime_api.dto.producto.CrearProductoDTO;
import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import com.futbolprime.futbolprime_api.model.Producto;
import com.futbolprime.futbolprime_api.repository.MarcaRepository;
import com.futbolprime.futbolprime_api.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final MarcaRepository marcaRepository;

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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado"
                ));
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

        if (dto.getSku() == null || dto.getSku().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El SKU es obligatorio");
        }
        String skuNormalizado = dto.getSku().trim();

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
        String nombreNormalizado = dto.getNombre().trim();

        if (dto.getPrecio() == null || dto.getPrecio() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio es obligatorio y debe ser >= 0");
        }

        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El stock es obligatorio y debe ser >= 0");
        }

        if (productoRepository.findBySku(skuNormalizado).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un producto con ese SKU: " + skuNormalizado);
        }

        if (dto.getMarcaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "marcaId es obligatorio");
        }
        var marcaOpt = marcaRepository.findById(dto.getMarcaId());
        var marca = marcaOpt.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Marca no encontrada con id: " + dto.getMarcaId()
        ));

        Producto producto = Producto.builder()
                .sku(skuNormalizado)
                .nombre(nombreNormalizado)
                .precio(dto.getPrecio())
                .oferta(dto.getOferta())
                .tipo(dto.getTipo() != null ? dto.getTipo().trim() : null)
                .talla(dto.getTalla() != null ? dto.getTalla().trim() : null)
                .color(dto.getColor() != null ? dto.getColor().trim() : null)
                .stock(dto.getStock())
                .marca(marca)
                .descripcion(dto.getDescripcion() != null ? dto.getDescripcion().trim() : null)
                .imagen(dto.getImagen() != null ? dto.getImagen().trim() : null)
                .build();

        Producto guardado = productoRepository.save(producto);
        return toDTO(guardado);
    }


    @Override
    public void eliminarPorSku(String sku) {
        var producto = productoRepository.findBySku(sku)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado"
                ));

        productoRepository.delete(producto);
    }

    @Override
    public ProductoDTO actualizarProducto(String sku, ActualizarProductoDTO dto) {

        var producto = productoRepository.findBySku(sku)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado"
                ));

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
        dto.setImagen(p.getImagen());
        if (p.getMarca() != null) {
            dto.setMarcaId(p.getMarca().getId());
            dto.setMarcaNombre(p.getMarca().getNombre());
        }
        return dto;
    }

}
