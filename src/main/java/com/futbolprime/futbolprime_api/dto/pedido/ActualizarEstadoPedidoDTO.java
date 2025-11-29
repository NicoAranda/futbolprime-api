package com.futbolprime.futbolprime_api.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "ActualizarEstadoPedidoDTO",
        description = "DTO utilizado para actualizar el estado de un pedido."
)
public class ActualizarEstadoPedidoDTO {

    @Schema(
            description = "Nuevo estado del pedido. Puede ser: PENDIENTE, PAGADO, ENVIADO, COMPLETADO, CANCELADO.",
            example = "ENVIADO",
            required = true
    )
    private String estado;
}
