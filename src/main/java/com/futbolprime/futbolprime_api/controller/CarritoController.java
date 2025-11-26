package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.carrito.ActualizarCarritoItemDTO;
import com.futbolprime.futbolprime_api.dto.carrito.CarritoItemDTO;
import com.futbolprime.futbolprime_api.dto.carrito.CrearCarritoItemDTO;
import com.futbolprime.futbolprime_api.dto.carrito.CarritoDTO;
import com.futbolprime.futbolprime_api.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CarritoDTO> obtenerCarrito(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoActivoPorUsuario(usuarioId));
    }

    @PostMapping("/item")
    public ResponseEntity<CarritoItemDTO> agregarItem(@RequestBody CrearCarritoItemDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.agregarProductoACarrito(dto));
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<CarritoItemDTO> actualizarItem(@PathVariable Long itemId, @RequestBody ActualizarCarritoItemDTO dto) {
        CarritoItemDTO actualizado = carritoService.actualizarItem(itemId, dto);
        if (actualizado == null) return ResponseEntity.noContent().build(); // si se eliminó
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{carritoId}/producto/{productoId}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long carritoId, @PathVariable Long productoId) {
        carritoService.eliminarItem(carritoId, productoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{carritoId}/vaciar")
    public ResponseEntity<Void> vaciar(@PathVariable Long carritoId) {
        carritoService.vaciarCarrito(carritoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{carritoId}/items")
    public ResponseEntity<List<CarritoItemDTO>> listarItems(@PathVariable Long carritoId) {
        return ResponseEntity.ok(carritoService.listarItems(carritoId));
    }

}
