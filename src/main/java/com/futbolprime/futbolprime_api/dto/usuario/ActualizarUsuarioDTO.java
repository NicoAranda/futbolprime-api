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
        name = "ActualizarUsuarioDTO",
        description = "DTO utilizado para actualizar la información de un usuario. Todos los campos son opcionales."
)
public class ActualizarUsuarioDTO {

    @Schema(
            description = "Nuevo nombre del usuario",
            example = "Fernanda Soto"
    )
    private String nombre;

    @Schema(
            description = "Nuevo correo electrónico del usuario",
            example = "fernanda@example.com"
    )
    private String email;

    @Schema(
            description = "Nueva contraseña del usuario. Debe enviarse en formato plano; será encriptada internamente.",
            example = "NuevaPass2024!"
    )
    private String password;

    @Schema(
            description = "Rol asignado al usuario (ADMIN, CLIENTE, etc.)",
            example = "CLIENTE"
    )
    private String rol;

    @Schema(
            description = "Indica si el usuario está habilitado o bloqueado para acceder al sistema",
            example = "true"
    )
    private Boolean habilitado;
}
