package com.futbolprime.futbolprime_api.dto.carrito;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CrearCarritoItemDTO",
        description = "DTO utilizado para agregar un nuevo producto al carrito de un usuario."
)
public class CrearCarritoItemDTO {

    @Schema(
            description = "ID del usuario al que pertenece el carrito",
            example = "7",
            required = true
    )
    private Long usuarioId;

    @Schema(
            description = "ID del producto que se desea agregar al carrito",
            example = "15",
            required = true
    )
    private Long productoId;

    @Schema(
            description = "Cantidad del producto que se agregará al carrito",
            example = "2",
            required = true
    )
    private Integer cantidad;
}
