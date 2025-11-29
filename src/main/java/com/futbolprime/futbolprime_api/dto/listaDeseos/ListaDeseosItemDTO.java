package com.futbolprime.futbolprime_api.dto.listaDeseos;

import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "ListaDeseosItemDTO",
        description = "Representa un producto dentro de la lista de deseos del usuario."
)
public class ListaDeseosItemDTO {

    @Schema(
            description = "ID único del ítem dentro de la lista de deseos",
            example = "21"
    )
    private Long id;

    @Schema(
            description = "Datos del producto asociado al ítem",
            implementation = ProductoDTO.class
    )
    private ProductoDTO producto;
}
