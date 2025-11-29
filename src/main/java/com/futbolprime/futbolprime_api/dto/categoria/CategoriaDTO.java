package com.futbolprime.futbolprime_api.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CategoriaDTO",
        description = "Representa una categoría dentro del catálogo, incluyendo información jerárquica."
)
public class CategoriaDTO {

    @Schema(
            description = "ID único de la categoría",
            example = "3"
    )
    private Long id;

    @Schema(
            description = "Nombre visible de la categoría",
            example = "Camisetas"
    )
    private String nombre;

    @Schema(
            description = "Slug único utilizado para URLs amigables",
            example = "camisetas"
    )
    private String slug;

    @Schema(
            description = "ID de la categoría padre si pertenece a una jerarquía. Puede ser null.",
            example = "1",
            nullable = true
    )
    private Long padreId;
}
