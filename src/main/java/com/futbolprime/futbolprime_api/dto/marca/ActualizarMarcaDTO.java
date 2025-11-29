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
        name = "ActualizarMarcaDTO",
        description = "DTO utilizado para actualizar los datos de una marca. Todos los campos son opcionales."
)
public class ActualizarMarcaDTO {

    @Schema(
            description = "Nuevo nombre visible de la marca (opcional).",
            example = "Adidas"
    )
    private String nombre;

    @Schema(
            description = "Nuevo slug único de la marca, usado para URLs amigables.",
            example = "adidas"
    )
    private String slug;
}
