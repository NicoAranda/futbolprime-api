package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.carrito.*;
import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import com.futbolprime.futbolprime_api.model.Carrito;
import com.futbolprime.futbolprime_api.model.CarritoItem;
import com.futbolprime.futbolprime_api.model.Producto;
import com.futbolprime.futbolprime_api.model.Usuario;
import com.futbolprime.futbolprime_api.repository.CarritoItemRepository;
import com.futbolprime.futbolprime_api.repository.CarritoRepository;
import com.futbolprime.futbolprime_api.repository.ProductoRepository;
import com.futbolprime.futbolprime_api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;


    @Override
    public CarritoDTO obtenerCarritoActivoPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuarioAndEstado(usuario, "ACTIVO")
                .orElseGet(() -> {
                    Carrito nuevo = Carrito.builder()
                            .usuario(usuario)
                            .estado("ACTIVO")
                            .build();
                    return carritoRepository.save(nuevo);
                });

        return toCarritoDTO(carrito);
    }

    @Override
    public CarritoItemDTO agregarProductoACarrito(CrearCarritoItemDTO dto) {
        if (dto.getCantidad() == null || dto.getCantidad() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cantidad debe ser >= 1");

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        Carrito carrito = carritoRepository.findByUsuarioAndEstado(usuario, "ACTIVO")
                .orElseGet(() -> carritoRepository.save(Carrito.builder().usuario(usuario).estado("ACTIVO").build()));

        var optItem = carritoItemRepository.findByCarritoAndProducto(carrito, producto);
        if (optItem.isPresent()) {
            CarritoItem existing = optItem.get();
            existing.setCantidad(existing.getCantidad() + dto.getCantidad());
            CarritoItem actualizado = carritoItemRepository.save(existing);
            return toItemDTO(actualizado);
        }
        CarritoItem item = CarritoItem.builder()
                .carrito(carrito)
                .producto(producto)
                .cantidad(dto.getCantidad())
                .precioUnitSnap(producto.getPrecio())
                .build();

        CarritoItem guardado = carritoItemRepository.save(item);
        carrito.getItems().add(guardado);

        return toItemDTO(guardado);
    }

    @Override
    public CarritoItemDTO actualizarItem(Long itemId, ActualizarCarritoItemDTO dto) {
        if (dto.getCantidad() == null || dto.getCantidad() < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cantidad inválida");

        CarritoItem item = carritoItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item no encontrado"));

        if (dto.getCantidad() == 0) {
            carritoItemRepository.delete(item);
            return null;
        }

        item.setCantidad(dto.getCantidad());
        CarritoItem actualizado = carritoItemRepository.save(item);
        return toItemDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminarItem(Long carritoId, Long productoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        carritoItemRepository.deleteByCarritoAndProducto(carrito, producto);
    }

    @Override
    @Transactional
    public void vaciarCarrito(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        carritoItemRepository.deleteByCarrito(carrito);
    }

    @Override
    public List<CarritoItemDTO> listarItems(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));

        return carritoItemRepository.findByCarrito(carrito)
                .stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());
    }

    private CarritoDTO toCarritoDTO(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setId(carrito.getId());
        dto.setUsuarioId(carrito.getUsuario().getId());
        dto.setEstado(carrito.getEstado());
        dto.setItems(carritoItemRepository.findByCarrito(carrito).stream().map(this::toItemDTO).collect(Collectors.toList()));
        return dto;
    }

    private CarritoItemDTO toItemDTO(CarritoItem item) {
        CarritoItemDTO dto = new CarritoItemDTO();
        dto.setId(item.getId());

        Producto p = item.getProducto();
        ProductoDTO pDto = new ProductoDTO();
        pDto.setId(p.getId());
        pDto.setSku(p.getSku());
        pDto.setNombre(p.getNombre());
        pDto.setPrecio(p.getPrecio());
        pDto.setOferta(p.getOferta());
        pDto.setTipo(p.getTipo());
        pDto.setTalla(p.getTalla());
        pDto.setColor(p.getColor());
        pDto.setStock(p.getStock());
        if (p.getMarca() != null) {
            pDto.setMarcaId(p.getMarca().getId());
            pDto.setMarcaNombre(p.getMarca().getNombre());
        }

        dto.setProducto(pDto);
        dto.setCantidad(item.getCantidad());
        dto.setPrecioUnitSnap(item.getPrecioUnitSnap());
        return dto;
    }
}
