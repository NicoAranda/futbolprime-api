package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.listaDeseos.CrearListaDeseosItemDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.ListaDeseosDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.ListaDeseosItemDTO;
import com.futbolprime.futbolprime_api.service.ListaDeseosService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

import java.util.List;

@RestController
@RequestMapping("/api/lista-deseos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Lista de Deseos", description = "Operaciones para gestionar la lista de deseos del usuario")
public class ListaDeseosController {

    private final ListaDeseosService listaDeseosService;

    // ========================================================================
    // Obtener lista de deseos del usuario
    // ========================================================================
    @Operation(
            summary = "Obtener la lista de deseos del usuario",
            description = "Devuelve la lista de deseos asociada al ID del usuario",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de deseos obtenida exitosamente",
                            content = @Content(schema = @Schema(implementation = ListaDeseosDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Lista de deseos no encontrada"
                    )
            }
    )
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ListaDeseosDTO> obtenerLista(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(listaDeseosService.obtenerPorUsuario(usuarioId));
    }

    // ========================================================================
    // Agregar producto a la lista de deseos
    // ========================================================================
    @Operation(
            summary = "Agregar producto a la lista de deseos",
            description = "Permite añadir un producto a la lista de deseos del usuario",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Producto agregado a la lista de deseos",
                            content = @Content(schema = @Schema(implementation = ListaDeseosItemDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos enviados"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ListaDeseosItemDTO> agregarProducto(
            @RequestBody CrearListaDeseosItemDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(listaDeseosService.agregarProducto(dto));
    }

    // ========================================================================
    // Eliminar producto de la lista de deseos
    // ========================================================================
    @Operation(
            summary = "Eliminar producto de la lista de deseos",
            description = "Elimina un producto específico de la lista de deseos del usuario",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Producto eliminado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto o lista no encontrada"
                    )
            }
    )
    @DeleteMapping("/usuario/{usuarioId}/producto/{productoId}")
    public ResponseEntity<Void> eliminarProducto(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId
    ) {
        listaDeseosService.eliminarProducto(usuarioId, productoId);
        return ResponseEntity.noContent().build();
    }

    // ========================================================================
    // Listar productos de la lista de deseos
    // ========================================================================
    @Operation(
            summary = "Listar productos de la lista de deseos",
            description = "Devuelve todos los productos guardados en la lista de deseos del usuario",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Productos listados exitosamente",
                            content = @Content(schema = @Schema(implementation = ListaDeseosItemDTO.class))
                    )
            }
    )
    @GetMapping("/usuario/{usuarioId}/items")
    public ResponseEntity<List<ListaDeseosItemDTO>> listarItems(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(listaDeseosService.listarItems(usuarioId));
    }
}
