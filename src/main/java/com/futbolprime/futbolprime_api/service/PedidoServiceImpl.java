package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.pago.PagoDTO;
import com.futbolprime.futbolprime_api.dto.pago.RegistrarPagoDTO;
import com.futbolprime.futbolprime_api.dto.pedido.*;
import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import com.futbolprime.futbolprime_api.model.*;
import com.futbolprime.futbolprime_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final PagoRepository pagoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public PedidoDTO crearPedido(CrearPedidoDTO dto) {
        if (dto.getItems() == null || dto.getItems().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido debe tener al menos un item");

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .estado("CREADO")
                .envio(dto.getEnvio() != null ? dto.getEnvio() : 0)
                .descuento(dto.getDescuento() != null ? dto.getDescuento() : 0)
                .build();

        int subtotal = 0;
        for (CrearPedidoItemDTO it : dto.getItems()) {
            Producto producto = productoRepository.findById(it.getProductoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado: " + it.getProductoId()));

            if (it.getCantidad() == null || it.getCantidad() <= 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cantidad inválida para producto " + it.getProductoId());

            // precio desde producto (snapshot)
            Integer price = producto.getPrecio(); // asumiendo Integer en Producto
            int line = price * it.getCantidad();
            subtotal += line;

            PedidoItem item = PedidoItem.builder()
                    .pedido(pedido)
                    .producto(producto)
                    .cantidad(it.getCantidad())
                    .precioUnitSnap(price)
                    .build();

            pedido.getItems().add(item);
        }

        pedido.setSubtotal(subtotal);
        int total = subtotal + pedido.getEnvio() - pedido.getDescuento();
        pedido.setTotal(total);

        // direccion
        pedido.setDirNombre(dto.getDirNombre());
        pedido.setDirLinea1(dto.getDirLinea1());
        pedido.setDirLinea2(dto.getDirLinea2());
        pedido.setDirCiudad(dto.getDirCiudad());
        pedido.setDirRegion(dto.getDirRegion());
        pedido.setDirZip(dto.getDirZip());
        pedido.setDirPais(dto.getDirPais());
        pedido.setDirTelefono(dto.getDirTelefono());

        Pedido guardado = pedidoRepository.save(pedido);
        return toDTO(guardado);
    }

    @Override
    public PedidoDTO obtenerPedido(Long id) {
        Pedido p = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        return toDTO(p);
    }

    @Override
    public List<PedidoDTO> listarPedidosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return pedidoRepository.findByUsuarioOrderByIdDesc(usuario)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoDTO actualizarEstado(Long id, ActualizarEstadoPedidoDTO dto) {
        Pedido p = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        p.setEstado(dto.getEstado());
        Pedido saved = pedidoRepository.save(p);
        return toDTO(saved);
    }

    @Override
    public PagoDTO registrarPago(Long pedidoId, RegistrarPagoDTO dto) {
        Pedido p = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        // Validación de monto
        if (dto.getMonto() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "monto requerido");
        }
        if (!dto.getMonto().equals(p.getTotal())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "monto inválido: debe coincidir con el total del pedido (" + p.getTotal() + ")");
        }

        // Si no viene transaccionRef, rechazamos para evitar duplicados sin referencia
        if (dto.getTransaccionRef() == null || dto.getTransaccionRef().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "transaccionRef requerida para idempotencia");
        }

        // Buscar todos los pagos con esa transaccionRef (puede haber >1 si hubo datos previos duplicados)
        List<Pago> encontrados = pagoRepository.findAllByTransaccionRef(dto.getTransaccionRef());

        if (!encontrados.isEmpty()) {
            if (encontrados.size() == 1) {
                // caso normal de duplicado: ya existe un pago con esa referencia -> 409
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Pago con transaccionRef ya registrado: " + dto.getTransaccionRef());
            } else {
                // hay múltiples pagos con la misma transaccionRef => estado inconsistente en BD
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Se encontraron múltiples pagos con transaccionRef=" + dto.getTransaccionRef()
                                + ". Por favor limpiar duplicados en la base de datos antes de reintentar.");
            }
        }

        // Crear y persistir pago
        Pago pago = Pago.builder()
                .pedido(p)
                .proveedor(dto.getProveedor())
                .metodo(dto.getMetodo())
                .monto(dto.getMonto())
                .estado(dto.getEstado())
                .transaccionRef(dto.getTransaccionRef())
                .build();

        Pago saved = pagoRepository.save(pago);
        p.getPagos().add(saved);

        if ("APROBADO".equalsIgnoreCase(dto.getEstado()) || "COMPLETADO".equalsIgnoreCase(dto.getEstado())) {
            p.setEstado("PAGADO");
            pedidoRepository.save(p);
        }

        PagoDTO dtoResp = new PagoDTO();
        dtoResp.setId(saved.getId());
        dtoResp.setProveedor(saved.getProveedor());
        dtoResp.setMetodo(saved.getMetodo());
        dtoResp.setMonto(saved.getMonto());
        dtoResp.setEstado(saved.getEstado());
        dtoResp.setTransaccionRef(saved.getTransaccionRef());
        return dtoResp;
    }

    private PedidoDTO toDTO(Pedido p) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(p.getId());
        dto.setUsuarioId(p.getUsuario().getId());
        dto.setEstado(p.getEstado());
        dto.setSubtotal(p.getSubtotal());
        dto.setEnvio(p.getEnvio());
        dto.setDescuento(p.getDescuento());
        dto.setTotal(p.getTotal());
        dto.setDirNombre(p.getDirNombre());
        dto.setDirLinea1(p.getDirLinea1());
        dto.setDirLinea2(p.getDirLinea2());
        dto.setDirCiudad(p.getDirCiudad());
        dto.setDirRegion(p.getDirRegion());
        dto.setDirZip(p.getDirZip());
        dto.setDirPais(p.getDirPais());
        dto.setDirTelefono(p.getDirTelefono());

        List<PedidoItemDTO> items = p.getItems().stream().map(item -> {
            PedidoItemDTO itDto = new PedidoItemDTO();
            itDto.setId(item.getId());
            Producto prod = item.getProducto();
            ProductoDTO prodDto = new ProductoDTO();
            prodDto.setId(prod.getId());
            prodDto.setSku(prod.getSku());
            prodDto.setNombre(prod.getNombre());
            prodDto.setPrecio(prod.getPrecio());
            prodDto.setOferta(prod.getOferta());
            prodDto.setTalla(prod.getTalla());
            prodDto.setColor(prod.getColor());
            prodDto.setStock(prod.getStock());
            if (prod.getMarca() != null) {
                prodDto.setMarcaId(prod.getMarca().getId());
                prodDto.setMarcaNombre(prod.getMarca().getNombre());
            }
            itDto.setProducto(prodDto);
            itDto.setCantidad(item.getCantidad());
            itDto.setPrecioUnitSnap(item.getPrecioUnitSnap());
            return itDto;
        }).collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }
}
