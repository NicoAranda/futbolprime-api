package com.futbolprime.futbolprime_api.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "LoginResponseDTO",
        description = "Respuesta enviada después de un login exitoso. Contiene los datos básicos del usuario autenticado."
)
public class LoginResponseDTO {

    @Schema(
            description = "ID único del usuario",
            example = "12"
    )
    private Long id;

    @Schema(
            description = "Nombre del usuario autenticado",
            example = "Fernanda Soto"
    )
    private String nombre;

    @Schema(
            description = "Correo electrónico del usuario autenticado",
            example = "fernanda@example.com"
    )
    private String email;

    @Schema(
            description = "Rol del usuario (CLIENTE, ADMIN, etc.)",
            example = "CLIENTE"
    )
    private String rol;
    private String token;
}
