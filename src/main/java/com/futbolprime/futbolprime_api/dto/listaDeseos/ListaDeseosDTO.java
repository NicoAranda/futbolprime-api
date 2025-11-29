package com.futbolprime.futbolprime_api.dto.listaDeseos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(
        name = "ListaDeseosDTO",
        description = "Representa la lista de deseos perteneciente a un usuario, incluyendo los productos agregados."
)
public class ListaDeseosDTO {

    @Schema(
            description = "ID único de la lista de deseos",
            example = "4"
    )
    private Long id;

    @Schema(
            description = "ID del usuario dueño de la lista de deseos",
            example = "12"
    )
    private Long usuarioId;

    @Schema(
            description = "Colección de productos agregados a la lista de deseos",
            implementation = ListaDeseosItemDTO.class
    )
    private List<ListaDeseosItemDTO> items;
}
