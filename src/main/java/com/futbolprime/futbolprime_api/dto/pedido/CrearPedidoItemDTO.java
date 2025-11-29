package com.futbolprime.futbolprime_api.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CrearPedidoItemDTO",
        description = "DTO que representa un ítem dentro del pedido, indicando producto y cantidad solicitada."
)
public class CrearPedidoItemDTO {

    @Schema(
            description = "ID del producto que se está agregando al pedido",
            example = "45",
            required = true
    )
    private Long productoId;

    @Schema(
            description = "Cantidad solicitada del producto",
            example = "2",
            required = true
    )
    private Integer cantidad;
}
