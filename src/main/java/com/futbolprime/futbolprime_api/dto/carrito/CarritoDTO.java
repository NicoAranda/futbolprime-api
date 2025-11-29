package com.futbolprime.futbolprime_api.dto.carrito;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(
        name = "CarritoDTO",
        description = "Representa el carrito activo de un usuario, incluyendo su estado e ítems agregados."
)
public class CarritoDTO {

    @Schema(
            description = "ID único del carrito",
            example = "15"
    )
    private Long id;

    @Schema(
            description = "ID del usuario dueño del carrito",
            example = "7"
    )
    private Long usuarioId;

    @Schema(
            description = "Estado actual del carrito (ACTIVO, COMPLETADO, CANCELADO)",
            example = "ACTIVO"
    )
    private String estado;

    @Schema(
            description = "Lista de ítems agregados al carrito",
            implementation = CarritoItemDTO.class
    )
    private List<CarritoItemDTO> items;
}
