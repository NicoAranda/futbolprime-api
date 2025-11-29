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
        name = "CrearMarcaDTO",
        description = "DTO utilizado para registrar una nueva marca en el sistema."
)
public class CrearMarcaDTO {

    @Schema(
            description = "Nombre visible de la marca. Es obligatorio.",
            example = "Nike",
            required = true
    )
    private String nombre;

    @Schema(
            description = "Slug único de la marca, usado para URLs amigables. Debe ser único.",
            example = "nike",
            required = true
    )
    private String slug;
}
