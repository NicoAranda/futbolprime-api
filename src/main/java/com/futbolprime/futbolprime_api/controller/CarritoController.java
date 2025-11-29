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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Carrito de Compras", description = "Operaciones para gestionar el carrito de compras del usuario")
public class CarritoController {

    private final CarritoService carritoService;

    // ============================================================
    // Obtener carrito activo por usuario
    // ============================================================
    @Operation(
            summary = "Obtener carrito activo del usuario",
            description = "Devuelve el carrito activo del usuario según su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Carrito obtenido exitosamente",
                            content = @Content(schema = @Schema(implementation = CarritoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Carrito no encontrado"
                    )
            }
    )
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CarritoDTO> obtenerCarrito(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoActivoPorUsuario(usuarioId));
    }

    // ============================================================
    // Agregar producto al carrito
    // ============================================================
    @Operation(
            summary = "Agregar producto al carrito",
            description = "Añade un producto al carrito del usuario. Si el producto ya existe, aumenta la cantidad.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Producto agregado al carrito",
                            content = @Content(schema = @Schema(implementation = CarritoItemDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos enviados"
                    )
            }
    )
    @PostMapping("/item")
    public ResponseEntity<CarritoItemDTO> agregarItem(@RequestBody CrearCarritoItemDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.agregarProductoACarrito(dto));
    }

    // ============================================================
    // Actualizar item del carrito
    // ============================================================
    @Operation(
            summary = "Actualizar un ítem del carrito",
            description = "Actualiza la cantidad de un ítem del carrito. Si la cantidad es 0, el ítem será eliminado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ítem actualizado exitosamente",
                            content = @Content(schema = @Schema(implementation = CarritoItemDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Ítem eliminado (cantidad establecida en 0)"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ítem no encontrado"
                    )
            }
    )
    @PutMapping("/item/{itemId}")
    public ResponseEntity<CarritoItemDTO> actualizarItem(@PathVariable Long itemId, @RequestBody ActualizarCarritoItemDTO dto) {
        CarritoItemDTO actualizado = carritoService.actualizarItem(itemId, dto);
        if (actualizado == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(actualizado);
    }

    // ============================================================
    // Eliminar un producto del carrito
    // ============================================================
    @Operation(
            summary = "Eliminar un producto del carrito",
            description = "Elimina un producto específico del carrito según su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Producto eliminado del carrito"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Carrito o producto no encontrado"
                    )
            }
    )
    @DeleteMapping("/{carritoId}/producto/{productoId}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long carritoId, @PathVariable Long productoId) {
        carritoService.eliminarItem(carritoId, productoId);
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // Vaciar carrito
    // ============================================================
    @Operation(
            summary = "Vaciar carrito",
            description = "Elimina todos los productos del carrito seleccionado",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Carrito vaciado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Carrito no encontrado"
                    )
            }
    )
    @DeleteMapping("/{carritoId}/vaciar")
    public ResponseEntity<Void> vaciar(@PathVariable Long carritoId) {
        carritoService.vaciarCarrito(carritoId);
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // Listar ítems de un carrito
    // ============================================================
    @Operation(
            summary = "Listar los ítems del carrito",
            description = "Devuelve todos los productos agregados al carrito",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ítems obtenidos exitosamente",
                            content = @Content(schema = @Schema(implementation = CarritoItemDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Carrito no encontrado"
                    )
            }
    )
    @GetMapping("/{carritoId}/items")
    public ResponseEntity<List<CarritoItemDTO>> listarItems(@PathVariable Long carritoId) {
        return ResponseEntity.ok(carritoService.listarItems(carritoId));
    }

}
