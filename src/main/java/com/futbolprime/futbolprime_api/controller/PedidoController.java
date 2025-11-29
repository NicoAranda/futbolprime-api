package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.pago.PagoDTO;
import com.futbolprime.futbolprime_api.dto.pago.RegistrarPagoDTO;
import com.futbolprime.futbolprime_api.dto.pedido.ActualizarEstadoPedidoDTO;
import com.futbolprime.futbolprime_api.dto.pedido.CrearPedidoDTO;
import com.futbolprime.futbolprime_api.dto.pedido.PedidoDTO;
import com.futbolprime.futbolprime_api.service.PedidoService;

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
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con la gestión de pedidos y pagos")
public class PedidoController {

    private final PedidoService pedidoService;

    // =========================================================================
    // Crear pedido
    // =========================================================================
    @Operation(
            summary = "Crear un nuevo pedido",
            description = "Genera un pedido nuevo asociado al usuario y al carrito enviado en el DTO",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Pedido creado exitosamente",
                            content = @Content(schema = @Schema(implementation = PedidoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos enviados"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody CrearPedidoDTO dto) {
        PedidoDTO creado = pedidoService.crearPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // =========================================================================
    // Obtener pedido por ID
    // =========================================================================
    @Operation(
            summary = "Obtener pedido por ID",
            description = "Devuelve la información completa del pedido solicitado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pedido encontrado",
                            content = @Content(schema = @Schema(implementation = PedidoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pedido no encontrado"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPedido(id));
    }

    // =========================================================================
    // Listar pedidos por usuario
    // =========================================================================
    @Operation(
            summary = "Listar pedidos por ID de usuario",
            description = "Devuelve todos los pedidos realizados por el usuario especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pedidos obtenidos exitosamente",
                            content = @Content(schema = @Schema(implementation = PedidoDTO.class))
                    )
            }
    )
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorUsuario(usuarioId));
    }

    // =========================================================================
    // Actualizar estado de un pedido
    // =========================================================================
    @Operation(
            summary = "Actualizar el estado de un pedido",
            description = "Permite modificar el estado del pedido: ejemplo → Pendiente, Pagado, Enviado, Completado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estado actualizado correctamente",
                            content = @Content(schema = @Schema(implementation = PedidoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pedido no encontrado"
                    )
            }
    )
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestBody ActualizarEstadoPedidoDTO dto
    ) {
        return ResponseEntity.ok(pedidoService.actualizarEstado(id, dto));
    }

    // =========================================================================
    // Registrar pago
    // =========================================================================
    @Operation(
            summary = "Registrar un pago",
            description = "Registra el pago de un pedido y asocia la información del método de pago",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Pago registrado exitosamente",
                            content = @Content(schema = @Schema(implementation = PagoDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos enviados"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pedido no encontrado"
                    )
            }
    )
    @PostMapping("/{id}/pagos")
    public ResponseEntity<PagoDTO> registrarPago(
            @PathVariable Long id,
            @RequestBody RegistrarPagoDTO dto
    ) {
        PagoDTO pago = pedidoService.registrarPago(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pago);
    }
}
