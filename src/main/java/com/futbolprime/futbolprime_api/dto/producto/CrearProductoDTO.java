package com.futbolprime.futbolprime_api.dto.producto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CrearProductoDTO",
        description = "DTO utilizado para registrar un nuevo producto en el catálogo."
)
public class CrearProductoDTO {

    @Schema(
            description = "SKU único del producto. No debe repetirse.",
            example = "CAM-RM-2024-001",
            required = true
    )
    private String sku;

    @Schema(
            description = "Nombre descriptivo del producto",
            example = "Camiseta Real Madrid 2024 Local",
            required = true
    )
    private String nombre;

    @Schema(
            description = "Precio del producto en CLP",
            example = "49990",
            required = true
    )
    private Integer precio;

    @Schema(
            description = "Porcentaje de oferta aplicado (0–100). 0 si no tiene oferta.",
            example = "10"
    )
    private Integer oferta;

    @Schema(
            description = "Tipo de producto (camiseta, balón, accesorio, etc.)",
            example = "camiseta",
            required = true
    )
    private String tipo;

    @Schema(
            description = "Talla del producto (si aplica)",
            example = "L",
            nullable = true
    )
    private String talla;

    @Schema(
            description = "Color del producto",
            example = "Blanco"
    )
    private String color;

    @Schema(
            description = "Stock disponible para venta",
            example = "25",
            required = true
    )
    private Integer stock;

    @Schema(
            description = "ID de la marca asociada al producto",
            example = "2",
            required = true
    )
    private Long marcaId;

    @Schema(
            description = "Descripción completa del producto",
            example = "Camiseta oficial temporada 2024, tecnología Aeroready."
    )
    private String descripcion;

    @Schema(
            description = "Imagen del producto en formato URL o Base64",
            example = "https://futbolprime.cl/images/camiseta-rm-2024.png"
    )
    private String imagen;
}
