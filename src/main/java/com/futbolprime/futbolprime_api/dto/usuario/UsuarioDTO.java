package com.futbolprime.futbolprime_api.dto.usuario;

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
        name = "UsuarioDTO",
        description = "Representa la información básica de un usuario registrado en el sistema."
)
public class UsuarioDTO {

    @Schema(
            description = "ID único del usuario",
            example = "5"
    )
    private Long id;

    @Schema(
            description = "Nombre completo del usuario",
            example = "Fernanda Soto"
    )
    private String nombre;

    @Schema(
            description = "Correo electrónico del usuario",
            example = "fernanda@example.com"
    )
    private String email;

    @Schema(
            description = "Rol asignado al usuario dentro del sistema",
            example = "CLIENTE"
    )
    private String rol;
}
