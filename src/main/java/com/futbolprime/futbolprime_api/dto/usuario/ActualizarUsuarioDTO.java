package com.futbolprime.futbolprime_api.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizarUsuarioDTO {
    private String nombre;
    private String email;
    private String password;
    private String rol;
    private Boolean habilitado;
}
