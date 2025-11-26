package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.pago.PagoDTO;
import com.futbolprime.futbolprime_api.dto.pago.RegistrarPagoDTO;
import com.futbolprime.futbolprime_api.dto.pedido.ActualizarEstadoPedidoDTO;
import com.futbolprime.futbolprime_api.dto.pedido.CrearPedidoDTO;
import com.futbolprime.futbolprime_api.dto.pedido.PedidoDTO;

import java.util.List;

public interface PedidoService {
    PedidoDTO crearPedido(CrearPedidoDTO dto);

    PedidoDTO obtenerPedido(Long id);

    List<PedidoDTO> listarPedidosPorUsuario(Long usuarioId);

    PedidoDTO actualizarEstado(Long id, ActualizarEstadoPedidoDTO dto);

    PagoDTO registrarPago(Long pedidoId, RegistrarPagoDTO dto);
}
