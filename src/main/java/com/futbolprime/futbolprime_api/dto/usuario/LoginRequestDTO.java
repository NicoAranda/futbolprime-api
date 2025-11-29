package com.futbolprime.futbolprime_api.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "LoginRequestDTO",
        description = "DTO utilizado para solicitar el inicio de sesión de un usuario."
)
public class LoginRequestDTO {

    @Schema(
            description = "Correo electrónico del usuario registrado",
            example = "usuario@example.com",
            required = true
    )
    private String email;

    @Schema(
            description = "Contraseña del usuario en texto plano",
            example = "MiPasswordSegura123",
            required = true
    )
    private String password;
}
