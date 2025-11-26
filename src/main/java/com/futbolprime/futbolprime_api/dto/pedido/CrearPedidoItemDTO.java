package com.futbolprime.futbolprime_api.dto.pedido;

import lombok.Data;

@Data
public class CrearPedidoItemDTO {
    private Long productoId;
    private Integer cantidad;
}
