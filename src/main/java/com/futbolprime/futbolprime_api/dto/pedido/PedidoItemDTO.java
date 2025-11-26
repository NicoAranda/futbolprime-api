package com.futbolprime.futbolprime_api.dto.pedido;

import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import lombok.Data;

@Data
public class PedidoItemDTO {

    private Long id;
    private ProductoDTO producto;
    private Integer cantidad;
    private Integer precioUnitSnap;
}
