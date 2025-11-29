package com.futbolprime.futbolprime_api.dto.pedido;

import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "PedidoItemDTO",
        description = "Representa un ítem dentro de un pedido, incluyendo producto, cantidad y precio unitario del momento de compra."
)
public class PedidoItemDTO {

    @Schema(
            description = "ID único del ítem dentro del pedido",
            example = "88"
    )
    private Long id;

    @Schema(
            description = "Información del producto comprado",
            implementation = ProductoDTO.class
    )
    private ProductoDTO producto;

    @Schema(
            description = "Cantidad del producto adquirida en el pedido",
            example = "2"
    )
    private Integer cantidad;

    @Schema(
            description = "Precio unitario capturado al momento de realizar el pedido (snapshot para evitar cambios posteriores)",
            example = "19990"
    )
    private Integer precioUnitSnap;
}
