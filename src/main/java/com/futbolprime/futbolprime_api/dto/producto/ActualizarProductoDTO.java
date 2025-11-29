package com.futbolprime.futbolprime_api.dto.producto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "ActualizarProductoDTO",
        description = "DTO utilizado para actualizar los datos de un producto. Todos los campos son opcionales."
)
public class ActualizarProductoDTO {

    @Schema(
            description = "Nuevo nombre del producto",
            example = "Camiseta Adidas Real Madrid 2024"
    )
    private String nombre;

    @Schema(
            description = "Descripción detallada del producto",
            example = "Camiseta oficial de Real Madrid temporada 2024, tecnología Aeroready."
    )
    private String descripcion;

    @Schema(
            description = "Nuevo precio del producto (CLP)",
            example = "39990"
    )
    private Integer precio;

    @Schema(
            description = "Porcentaje de oferta aplicado al producto (0–100)",
            example = "20"
    )
    private Integer oferta;

    @Schema(
            description = "Nuevo stock disponible",
            example = "15"
    )
    private Integer stock;

    @Schema(
            description = "Tipo o categoría del producto (balón, camiseta, accesorio, etc.)",
            example = "camiseta"
    )
    private String tipo;

    @Schema(
            description = "Color del producto",
            example = "Blanco"
    )
    private String color;

    @Schema(
            description = "Talla del producto",
            example = "L"
    )
    private String talla;

    @Schema(
            description = "ID de la marca asociada al producto",
            example = "2"
    )
    private Long marcaId;
}
}
