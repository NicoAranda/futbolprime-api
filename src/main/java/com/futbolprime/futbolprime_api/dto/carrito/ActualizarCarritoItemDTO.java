package com.futbolprime.futbolprime_api.dto.carrito;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "ActualizarCarritoItemDTO",
        description = "DTO utilizado para actualizar la cantidad de un producto en el carrito"
)
public class ActualizarCarritoItemDTO {

    @Schema(
            description = "Nueva cantidad del producto. Si se envía 0, el ítem será eliminado del carrito.",
            example = "3",
            required = true
    )
    private Integer cantidad;
}
