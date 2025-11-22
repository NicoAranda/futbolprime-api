package com.futbolprime.futbolprime_api.dto.usuario;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
