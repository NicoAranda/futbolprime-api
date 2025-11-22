package com.futbolprime.futbolprime_api.dto.usuario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private String rol;
}
