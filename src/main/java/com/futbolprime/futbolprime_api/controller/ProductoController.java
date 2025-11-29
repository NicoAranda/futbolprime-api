package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.producto.ActualizarProductoDTO;
import com.futbolprime.futbolprime_api.dto.producto.CrearProductoDTO;
import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import com.futbolprime.futbolprime_api.service.ProductoService;

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
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Productos", description = "Operaciones relacionadas con la gestión de productos")
public class ProductoController {

    private final ProductoService productoService;

    // ============================================================================
    // Listar todos los productos
    // ============================================================================
    @Operation(
            summary = "Listar todos los productos",
            description = "Retorna la lista completa de productos registrados",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Productos obtenidos exitosamente",
                            content = @Content(schema = @Schema(implementation = ProductoDTO.class))
                    )
            }
    )
    @GetMapping
    public List<ProductoDTO> listarTodos() {
        return productoService.listarTodos();
    }

    // ============================================================================
    // Obtener producto por SKU
    // ============================================================================
    @Operation(
            summary = "Obtener un producto por SKU",
            description = "Busca y devuelve los datos de un producto utilizando su SKU",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto encontrado",
                            content = @Content(schema = @Schema(implementation = ProductoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado"
                    )
            }
    )
    @GetMapping("/{sku}")
    public ResponseEntity<ProductoDTO> obtenerPorSku(@PathVariable String sku) {
        ProductoDTO dto = productoService.buscarPorSku(sku);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    // ============================================================================
    // Listar productos por tipo
    // ============================================================================
    @Operation(
            summary = "Listar productos por tipo",
            description = "Devuelve todos los productos asociados al tipo especificado (ejemplo: balones, camisetas, accesorios)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Productos obtenidos exitosamente",
                            content = @Content(schema = @Schema(implementation = ProductoDTO.class))
                    )
            }
    )
    @GetMapping("/tipo/{tipo}")
    public List<ProductoDTO> listarPorTipo(@PathVariable String tipo) {
        return productoService.listarPorTipo(tipo);
    }

    // ============================================================================
    // Crear un nuevo producto
    // ============================================================================
    @Operation(
            summary = "Crear un nuevo producto",
            description = "Registra un nuevo producto en el sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Producto creado exitosamente",
                            content = @Content(schema = @Schema(implementation = ProductoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos enviados inválidos"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody CrearProductoDTO request) {
        ProductoDTO creado = productoService.crearProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ============================================================================
    // Eliminar producto por SKU
    // ============================================================================
    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina un producto del sistema utilizando su SKU",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Producto eliminado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado"
                    )
            }
    )
    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String sku) {
        productoService.eliminarPorSku(sku);
        return ResponseEntity.noContent().build();
    }

    // ============================================================================
    // Actualizar producto
    // ============================================================================
    @Operation(
            summary = "Actualizar un producto",
            description = "Modifica los datos de un producto existente. Solo se actualizan los campos enviados.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto actualizado exitosamente",
                            content = @Content(schema = @Schema(implementation = ProductoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado"
                    )
            }
    )
    @PutMapping("/{sku}")
    public ProductoDTO actualizarProducto(
            @PathVariable String sku,
            @RequestBody ActualizarProductoDTO dto
    ) {
        return productoService.actualizarProducto(sku, dto);
    }
}
