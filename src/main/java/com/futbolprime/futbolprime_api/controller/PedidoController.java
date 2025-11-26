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

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody CrearPedidoDTO dto) {
        PedidoDTO creado = pedidoService.crearPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPedido(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(pedidoService.listarPedidosPorUsuario(usuarioId));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> actualizarEstado(@PathVariable Long id, @RequestBody ActualizarEstadoPedidoDTO dto) {
        return ResponseEntity.ok(pedidoService.actualizarEstado(id, dto));
    }

    @PostMapping("/{id}/pagos")
    public ResponseEntity<PagoDTO> registrarPago(@PathVariable Long id, @RequestBody RegistrarPagoDTO dto) {
        PagoDTO pago = pedidoService.registrarPago(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pago);
    }
}
