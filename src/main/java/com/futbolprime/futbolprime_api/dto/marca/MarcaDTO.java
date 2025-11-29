package com.futbolprime.futbolprime_api.dto.marca;

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
        name = "MarcaDTO",
        description = "Representa una marca disponible en el catálogo de productos."
)
public class MarcaDTO {

    @Schema(
            description = "ID único de la marca",
            example = "3"
    )
    private Long id;

    @Schema(
            description = "Nombre visible de la marca",
            example = "Puma"
    )
    private String nombre;

    @Schema(
            description = "Slug único de la marca, usado para URLs amigables",
            example = "puma"
    )
    private String slug;
}
