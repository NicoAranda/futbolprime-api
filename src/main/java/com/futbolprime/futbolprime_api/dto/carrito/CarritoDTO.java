package com.futbolprime.futbolprime_api.dto.carrito;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CarritoDTO {

    private Long id;
    private Long usuarioId;
    private String estado;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}
