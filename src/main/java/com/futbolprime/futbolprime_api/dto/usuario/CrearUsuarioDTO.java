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
        name = "CrearUsuarioDTO",
        description = "DTO utilizado para registrar un nuevo usuario en el sistema."
)
public class CrearUsuarioDTO {

    @Schema(
            description = "Nombre completo del usuario",
            example = "Fernanda Soto",
            required = true
    )
    private String nombre;

    @Schema(
            description = "Correo electrónico del usuario. Debe ser único.",
            example = "fernanda@example.com",
            required = true
    )
    private String email;

    @Schema(
            description = "Contraseña del usuario en texto plano. Será encriptada internamente.",
            example = "PassSegura2024!",
            required = true
    )
    private String password;

    @Schema(
            description = "Rol asignado al usuario (por ejemplo: ADMIN, CLIENTE)",
            example = "CLIENTE",
            required = true
    )
    private String rol;

    @Schema(
            description = "Indica si el usuario estará habilitado para iniciar sesión",
            example = "true",
            required = true
    )
    private Boolean habilitado;
}
