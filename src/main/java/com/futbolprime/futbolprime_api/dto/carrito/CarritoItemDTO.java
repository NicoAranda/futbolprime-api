package com.futbolprime.futbolprime_api.dto.carrito;

import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CarritoItemDTO",
        description = "Representa un producto dentro del carrito, incluyendo su cantidad y precio al momento de agregarlo."
)
public class CarritoItemDTO {

    @Schema(
            description = "ID único del ítem dentro del carrito",
            example = "42"
    )
    private Long id;

    @Schema(
            description = "Información del producto asociado al ítem",
            implementation = ProductoDTO.class
    )
    private ProductoDTO producto;

    @Schema(
            description = "Cantidad del producto dentro del carrito",
            example = "2"
    )
    private Integer cantidad;

    @Schema(
            description = "Precio unitario capturado al momento de agregar el producto al carrito (snapshot), evita variaciones posteriores",
            example = "19990"
    )
    private Integer precioUnitSnap;
}
