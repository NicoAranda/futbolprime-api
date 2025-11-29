package com.futbolprime.futbolprime_api.dto.producto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "ProductoDTO",
        description = "Representa un producto disponible en el catálogo, incluyendo detalles, stock y su marca asociada."
)
public class ProductoDTO {

    @Schema(
            description = "ID único del producto",
            example = "101"
    )
    private Long id;

    @Schema(
            description = "SKU único del producto",
            example = "CAM-RM-2024-001"
    )
    private String sku;

    @Schema(
            description = "Nombre del producto",
            example = "Camiseta Real Madrid 2024 Local"
    )
    private String nombre;

    @Schema(
            description = "Precio del producto en CLP",
            example = "49990"
    )
    private Integer precio;

    @Schema(
            description = "Porcentaje de oferta aplicado al producto (0–100)",
            example = "10"
    )
    private Integer oferta;

    @Schema(
            description = "Cantidad disponible en stock",
            example = "20"
    )
    private Integer stock;

    @Schema(
            description = "Tipo o categoría del producto",
            example = "camiseta"
    )
    private String tipo;

    @Schema(
            description = "Talla del producto si corresponde",
            example = "L",
            nullable = true
    )
    private String talla;

    @Schema(
            description = "Color principal del producto",
            example = "Blanco"
    )
    private String color;

    @Schema(
            description = "URL o Base64 de la imagen del producto",
            example = "https://futbolprime.cl/images/camiseta-rm-2024.png"
    )
    private String imagen;

    @Schema(
            description = "ID de la marca asociada al producto",
            example = "2"
    )
    private Long marcaId;

    @Schema(
            description = "Nombre de la marca asociada",
            example = "Adidas"
    )
    private String marcaNombre;
}
