package com.futbolprime.futbolprime_api.dto.listaDeseos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CrearListaDeseosItemDTO",
        description = "DTO utilizado para agregar un producto a la lista de deseos de un usuario."
)
public class CrearListaDeseosItemDTO {

    @Schema(
            description = "ID del usuario dueño de la lista de deseos",
            example = "12",
            required = true
    )
    private Long usuarioId;

    @Schema(
            description = "ID del producto que se desea agregar a la lista de deseos",
            example = "55",
            required = true
    )
    private Long productoId;
}
