package com.futbolprime.futbolprime_api.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "ActualizarCategoriaDTO",
        description = "DTO utilizado para actualizar los datos de una categoría. Todos los campos son opcionales."
)
public class ActualizarCategoriaDTO {

    @Schema(
            description = "Nuevo nombre de la categoría (opcional)",
            example = "Camisetas Oficiales"
    )
    private String nombre;

    @Schema(
            description = "Nuevo slug único de la categoría. Se usa para URLs amigables.",
            example = "camisetas-oficiales"
    )
    private String slug;

    @Schema(
            description = "ID de la categoría padre, si la categoría debe quedar anidada",
            example = "1"
    )
    private Long padreId;
}
