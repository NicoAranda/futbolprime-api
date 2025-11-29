package com.futbolprime.futbolprime_api.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CrearCategoriaDTO",
        description = "DTO utilizado para crear una nueva categoría en el sistema."
)
public class CrearCategoriaDTO {

    @Schema(
            description = "Nombre visible de la categoría. Es obligatorio.",
            example = "Camisetas Oficiales",
            required = true
    )
    private String nombre;

    @Schema(
            description = "Slug único que se usará para URLs amigables. Debe ser único.",
            example = "camisetas-oficiales",
            required = true
    )
    private String slug;

    @Schema(
            description = "ID de la categoría padre si pertenece a una jerarquía. Puede ser null.",
            example = "1",
            nullable = true
    )
    private Long padreId;
}
